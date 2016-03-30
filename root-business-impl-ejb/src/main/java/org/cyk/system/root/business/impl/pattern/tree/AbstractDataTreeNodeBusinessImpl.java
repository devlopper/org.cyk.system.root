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
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeNodeDao;

public abstract class AbstractDataTreeNodeBusinessImpl<NODE extends AbstractDataTreeNode,DAO extends AbstractDataTreeNodeDao<NODE>>  
    extends AbstractEnumerationBusinessImpl<NODE, DAO> implements AbstractDataTreeNodeBusiness<NODE> {

	private static final long serialVersionUID = 8279530282390587764L;

	@Inject private NestedSetNodeBusiness nestedSetNodeBusiness;
	
	public AbstractDataTreeNodeBusinessImpl(DAO dao) {
        super(dao);
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
	public void move(NODE node, NODE parent) {
		nestedSetNodeBusiness.detach(node.getNode());
		
		//debug(node);
		/*List<NODE> tree = new ArrayList<>(dao.readByParent(node));
		dao.delete(node);
		node.getNode().setParent(parent.getNode());
		node.setIdentifier(null);
		node.getNode().setIdentifier(null);
		node = dao.create(node);
		//debug(node);
		
		for(NODE n : tree){
			//System.out.println("N");
			//debug(n);
			NODE nParentNode = null;
			for(NODE pn : tree)
				if( pn.getNode()!=null && n.getNode().getParent()!=null &&  pn.getNode().getIdentifier().equals(n.getNode().getParent().getIdentifier())){
					nParentNode = pn;
					break;
				}
			if(nParentNode==null)
				nParentNode = node;
			//System.out.println("Parent of "+n+" is "+nParentNode);
			n.getNode().setParent(nParentNode.getNode());
			n.setIdentifier(null);
			n.getNode().setIdentifier(null);
			
			//((GenericDaoImpl)genericDao).getEntityManager().detach(n.getNode());
			
			dao.create(n);
		}*/
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isAncestorOf(NODE ancestor,NODE child){
		/*
		NODE parent;
		while((parent=findParent(child))!=null)
			if(parent.equals(ancestor))
				return Boolean.TRUE;
			else
				child = parent;
		return Boolean.FALSE;
		*/
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
        //logStackTrace();
        Collection<NODE> hierarchy = dao.readRoots();
        for(NODE type : hierarchy)
            loadChildren(type);
        return hierarchy;
    }
    
    private void loadChildren(NODE parent){
       buildHierarchy(parent,new ArrayList<>(dao.readByParent(parent)));
    }
    
    @SuppressWarnings("unchecked")
    private void buildHierarchy(NODE parent,List<NODE> children){
        for(int i=0;i<children.size();){
            
            if(children.get(i).getNode().getParent().equals(parent.getNode())){
                if(parent.getChildren()==null)
                    parent.setChildren(new ArrayList<AbstractDataTreeNode>());
                parent.getChildren().add(children.remove(i));
            }else
                i++;
        }if(parent.getChildren()!=null)
            for(AbstractDataTreeNode child : parent.getChildren())
                buildHierarchy((NODE) child, children);
    }
    
}
