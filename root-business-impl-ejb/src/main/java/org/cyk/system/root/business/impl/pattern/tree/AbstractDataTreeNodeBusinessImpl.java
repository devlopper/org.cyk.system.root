package org.cyk.system.root.business.impl.pattern.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeNodeDao;

public abstract class AbstractDataTreeNodeBusinessImpl<NODE extends AbstractDataTreeNode,DAO extends AbstractDataTreeNodeDao<NODE>>  
    extends AbstractEnumerationBusinessImpl<NODE, DAO> implements AbstractDataTreeNodeBusiness<NODE> {

	private static final long serialVersionUID = 8279530282390587764L;

	public AbstractDataTreeNodeBusinessImpl(DAO dao) {
        super(dao);
    }
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public NODE findParent(NODE child){
		return dao.readParent(child);
	}
    
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
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
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isAtLeastOneAncestorOf(Collection<NODE> ancestors,NODE child){
		if(ancestors==null)
			return Boolean.FALSE;
		for(NODE node : ancestors)
			if(isAncestorOf(node, child))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public void findHierarchy(NODE anEnumeration) {
        loadChildren(anEnumeration);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
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
