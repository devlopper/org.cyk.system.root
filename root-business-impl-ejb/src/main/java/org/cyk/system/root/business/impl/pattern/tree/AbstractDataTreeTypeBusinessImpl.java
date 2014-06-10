package org.cyk.system.root.business.impl.pattern.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeTypeBusiness;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeTypeDao;

public abstract class AbstractDataTreeTypeBusinessImpl<DATA_TREE_TYPE extends DataTreeType,DAO extends AbstractDataTreeTypeDao<DATA_TREE_TYPE>>  
    extends AbstractDataTreeNodeBusinessImpl<DATA_TREE_TYPE, DAO> implements AbstractDataTreeTypeBusiness<DATA_TREE_TYPE> {

    public AbstractDataTreeTypeBusinessImpl(DAO dao) {
        super(dao);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public void findHierarchy(DATA_TREE_TYPE anEnumeration) {
        loadChildren(anEnumeration);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Collection<DATA_TREE_TYPE> findHierarchies() {
        Collection<DATA_TREE_TYPE> hierarchy = dao.readRoots();
        for(DATA_TREE_TYPE type : hierarchy)
            loadChildren(type);
        return hierarchy;
    }
    
    private void loadChildren(DATA_TREE_TYPE parent){
        buildHierarchy(parent,new ArrayList<>(dao.readByParent(parent)));
    }
    
    @SuppressWarnings("unchecked")
    private void buildHierarchy(DATA_TREE_TYPE parent,List<DATA_TREE_TYPE> children){
        for(int i=0;i<children.size();)
            if(children.get(i).getNode().getParent().equals(parent.getNode())){
                if(parent.getChildren()==null)
                    parent.setChildren(new ArrayList<DataTreeType>());
                parent.getChildren().add(children.remove(i));
            }else
                i++;
        if(parent.getChildren()!=null)
            for(DataTreeType child : parent.getChildren())
                buildHierarchy((DATA_TREE_TYPE) child, children);
    }

}
