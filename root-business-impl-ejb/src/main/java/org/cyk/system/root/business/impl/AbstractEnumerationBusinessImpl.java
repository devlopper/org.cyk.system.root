package org.cyk.system.root.business.impl;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public abstract class AbstractEnumerationBusinessImpl<ENUMERATION extends AbstractEnumeration,DAO extends AbstractEnumerationDao<ENUMERATION>> 
    extends AbstractTypedBusinessService<ENUMERATION, DAO> implements AbstractEnumerationBusiness<ENUMERATION> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2383681421917333298L;

	public AbstractEnumerationBusinessImpl(DAO dao) {
        super(dao);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ENUMERATION find(String code){
        return dao.read(code);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ENUMERATION load(String code) {
    	ENUMERATION enumeration = find(code);
    	load(enumeration);
    	return enumeration;
    }

}
