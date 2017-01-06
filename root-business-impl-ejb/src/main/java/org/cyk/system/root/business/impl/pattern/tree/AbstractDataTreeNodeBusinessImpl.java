package org.cyk.system.root.business.impl.pattern.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.api.pattern.tree.NestedSetNodeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeNodeDao;

public abstract class AbstractDataTreeNodeBusinessImpl<NODE extends AbstractDataTreeNode,DAO extends AbstractDataTreeNodeDao<NODE>>  
    extends AbstractEnumerationBusinessImpl<NODE, DAO> implements AbstractDataTreeNodeBusiness<NODE> {

	private static final long serialVersionUID = 8279530282390587764L;

	@Inject private NestedSetNodeBusiness nestedSetNodeBusiness;
	
	public AbstractDataTreeNodeBusinessImpl(DAO dao) {
        super(dao);
    }
	
	@Override
	public NODE create(NODE enumeration) {
		if(enumeration.getNode()==null){
			enumeration.setNode(new NestedSetNode(new NestedSet(), null));
			enumeration.getNode().getSet().setName(enumeration.getName());
		}
		if(enumeration.getNode().getIdentifier()==null){
			if(StringUtils.isBlank(enumeration.getNode().getCode()))
				enumeration.getNode().setCode(enumeration.getCode());
			if(StringUtils.isBlank(enumeration.getNode().getName()))
				enumeration.getNode().setName(enumeration.getName());
			nestedSetNodeBusiness.create(enumeration.getNode());
		}
		return super.create(enumeration);
	}
	
	@Override
	public NODE delete(NODE enumeration) {
		logTrace("Deleting {} on node {}. Children are the followings :", enumeration,enumeration.getNode());
		NestedSetNode rootNode = enumeration.getNode();
		Collection<NODE> list = dao.readByParent(enumeration);
		for(NODE element : list)
			logTrace("\tChild {} on node {}", element,element.getNode());
		list.add(enumeration);
		for(NODE e : list){
			e.setNode(null);
			dao.delete(e);
			logTrace("\t\tElement {} deleted", e);
		}
		nestedSetNodeBusiness.delete(rootNode);
		return enumeration;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<AbstractIdentifiable> findParentRecursively(NODE node) {
		Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
		for(NODE n : dao.readParentRecursively(node))
			identifiables.add(n);
		return identifiables;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void setParents(NODE node) {
		for(AbstractIdentifiable identifiable : findParentRecursively(node))
			node.getParents().add(identifiable);
	} 

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void setParents(Collection<NODE> nodes) {
		for(NODE node : nodes)
			setParents(node);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public NODE findParent(NODE child){
		return dao.readParent(child);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NODE> findByParent(NODE parent){
		return dao.readByParent(parent);
	}
	
	@Override
	public Collection<NODE> findByString(StringSearchCriteria stringSearchCriteria) {
		Collection<NODE> nodes = super.findByString(stringSearchCriteria);
		setParents(nodes);
		return nodes;
	}

	@Override
	public void move(NODE enumeration, NODE parent) {
		NestedSetNode node = enumeration.getNode();
		node = nestedSetNodeBusiness.detach(node);
		nestedSetNodeBusiness.attach(node,parent.getNode());
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isAncestorOf(NODE ancestor,NODE child){
		return ancestor.getNode().getLeftIndex() < child.getNode().getLeftIndex() && ancestor.getNode().getRightIndex() > child.getNode().getRightIndex();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isAtLeastOneAncestorOf(Collection<NODE> ancestors,NODE child){
		if(ancestors==null)
			return Boolean.FALSE;
		for(NODE node : ancestors)
			if(isAncestorOf(node, child))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void findHierarchy(NODE anEnumeration) {
        loadChildren(anEnumeration);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Collection<NODE> findHierarchies() {
        Collection<NODE> hierarchy = dao.readRoots();
        for(NODE type : hierarchy)
            loadChildren(type);
        return hierarchy;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public NODE instanciateOne(String parentCode, String code,String name) {
		NODE enumeration = instanciateOne(code,name);
    	enumeration.setParent(dao.readByGlobalIdentifierCode(parentCode));
    	return enumeration;
	}

	private void loadChildren(NODE parent){
       buildHierarchy(parent,new ArrayList<>(dao.readByParent(parent)));
    }
    
    @SuppressWarnings("unchecked")
    private void buildHierarchy(NODE parent,List<NODE> children){
        for(int i=0;i<children.size();){
            
            if(children.get(i).getNode().getParent().equals(parent.getNode())){
                if(parent.getChildren()==null)
                    parent.setChildren(new ArrayList<AbstractIdentifiable>());
                parent.getChildren().add(children.remove(i));
            }else
                i++;
        }if(parent.getChildren()!=null)
            for(AbstractIdentifiable child : parent.getChildren())
                buildHierarchy((NODE) child, children);
    }
    
	@Override
	protected NODE __instanciateOne__(String[] values, InstanciateOneListener<NODE> listener) {
		NODE node = super.__instanciateOne__(values, listener);
		set(listener.getSetListener().setIndex(getInstanciateOneDataTreeNodeStartIndex(values)), AbstractDataTree.FIELD_TYPE);
		return node;
	}
	
	protected Integer getInstanciateOneDataTreeNodeStartIndex(String[] values) {
		return 10;
	}
}
