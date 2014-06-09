package org.cyk.system.root.business.impl.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeTypeBusiness;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeTypeDao;

public abstract class AbstractDataTreeTypeBusinessImpl<ENUMERATION extends AbstractDataTreeType,DAO extends AbstractDataTreeTypeDao<ENUMERATION>>  
    extends AbstractDataTreeNodeBusinessImpl<ENUMERATION, DAO> implements AbstractDataTreeTypeBusiness<ENUMERATION> {

    public AbstractDataTreeTypeBusinessImpl(DAO dao) {
        super(dao);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void findHierarchy(ENUMERATION anEnumeration) {
        anEnumeration.setChildren((Collection<AbstractDataTreeType>) dao.readByParent(anEnumeration));   
    }
    
    @Override
    public Collection<ENUMERATION> findHierarchies() {
        // TODO Auto-generated method stub
        return null;
    }

}
