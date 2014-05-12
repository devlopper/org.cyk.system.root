package org.cyk.system.root.business.impl;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public abstract class AbstractEnumerationBusinessImpl<ENUMERATION extends AbstractEnumeration,DAO extends AbstractEnumerationDao<ENUMERATION>> 
    extends AbstractTypedBusinessService<ENUMERATION, DAO> implements AbstractDataTreeNodeBusiness<ENUMERATION> {

    public AbstractEnumerationBusinessImpl(DAO dao) {
        super(dao);
    }
    
    @Override
    public ENUMERATION find(String code){
        return dao.read(code);
    }

}
