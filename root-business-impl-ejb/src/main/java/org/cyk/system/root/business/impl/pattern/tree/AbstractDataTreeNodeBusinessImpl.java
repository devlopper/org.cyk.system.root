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

    public AbstractDataTreeNodeBusinessImpl(DAO dao) {
        super(dao);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public void findHierarchy(NODE anEnumeration) {
        loadChildren(anEnumeration);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Collection<NODE> findHierarchies() {
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
        for(int i=0;i<children.size();)
            if(children.get(i).getNode().getParent().equals(parent.getNode())){
                if(parent.getChildren()==null)
                    parent.setChildren(new ArrayList<AbstractDataTreeNode>());
                parent.getChildren().add(children.remove(i));
            }else
                i++;
        if(parent.getChildren()!=null)
            for(AbstractDataTreeNode child : parent.getChildren())
                buildHierarchy((NODE) child, children);
    }

}
