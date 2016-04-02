package org.cyk.system.root.business.impl.pattern.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.api.pattern.tree.NestedSetNodeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeNodeDao;

public abstract class AbstractDataTreeNodeBusinessImpl<ENUMERATION extends AbstractDataTreeNode,DAO extends AbstractDataTreeNodeDao<ENUMERATION>>  
    extends AbstractEnumerationBusinessImpl<ENUMERATION, DAO> implements AbstractDataTreeNodeBusiness<ENUMERATION> {

	private static final long serialVersionUID = 8279530282390587764L;

	@Inject private NestedSetNodeBusiness nestedSetNodeBusiness;
	
	public AbstractDataTreeNodeBusinessImpl(DAO dao) {
        super(dao);
    }
	
	@Override
	public ENUMERATION create(ENUMERATION enumeration) {
		if(enumeration.getNode()==null)
			enumeration.setNode(new NestedSetNode(new NestedSet(), null));
		
		if(enumeration.getNode().getIdentifier()==null)
			nestedSetNodeBusiness.create(enumeration.getNode());
		return super.create(enumeration);
	}
	
	@Override
	public ENUMERATION delete(ENUMERATION enumeration) {
		Collection<ENUMERATION> list = dao.readByParent(enumeration);
		list.add(enumeration);
		for(ENUMERATION e : list){
			e.setNode(null);
			dao.delete(e);
		}
		nestedSetNodeBusiness.delete(enumeration.getNode());
		return enumeration;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public ENUMERATION findParent(ENUMERATION child){
		return dao.readParent(child);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<ENUMERATION> findByParent(ENUMERATION parent){
		return dao.readByParent(parent);
	}
	
	@Override
	public void move(ENUMERATION enumeration, ENUMERATION parent) {
		NestedSetNode node = enumeration.getNode();
		node = nestedSetNodeBusiness.detach(node);
		nestedSetNodeBusiness.attach(node,parent.getNode());
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isAncestorOf(ENUMERATION ancestor,ENUMERATION child){
		return ancestor.getNode().getLeftIndex() < child.getNode().getLeftIndex() && ancestor.getNode().getRightIndex() > child.getNode().getRightIndex();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isAtLeastOneAncestorOf(Collection<ENUMERATION> ancestors,ENUMERATION child){
		if(ancestors==null)
			return Boolean.FALSE;
		for(ENUMERATION node : ancestors)
			if(isAncestorOf(node, child))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void findHierarchy(ENUMERATION anEnumeration) {
        loadChildren(anEnumeration);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Collection<ENUMERATION> findHierarchies() {
        //logStackTrace();
        Collection<ENUMERATION> hierarchy = dao.readRoots();
        for(ENUMERATION type : hierarchy)
            loadChildren(type);
        return hierarchy;
    }
    
    private void loadChildren(ENUMERATION parent){
       buildHierarchy(parent,new ArrayList<>(dao.readByParent(parent)));
    }
    
    @SuppressWarnings("unchecked")
    private void buildHierarchy(ENUMERATION parent,List<ENUMERATION> children){
        for(int i=0;i<children.size();){
            
            if(children.get(i).getNode().getParent().equals(parent.getNode())){
                if(parent.getChildren()==null)
                    parent.setChildren(new ArrayList<AbstractDataTreeNode>());
                parent.getChildren().add(children.remove(i));
            }else
                i++;
        }if(parent.getChildren()!=null)
            for(AbstractDataTreeNode child : parent.getChildren())
                buildHierarchy((ENUMERATION) child, children);
    }
    
}
