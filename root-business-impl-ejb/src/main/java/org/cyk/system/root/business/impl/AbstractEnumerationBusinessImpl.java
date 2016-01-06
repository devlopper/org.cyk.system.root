package org.cyk.system.root.business.impl;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;
import org.cyk.utility.common.Constant;

public abstract class AbstractEnumerationBusinessImpl<ENUMERATION extends AbstractEnumeration,DAO extends AbstractEnumerationDao<ENUMERATION>> 
    extends AbstractTypedBusinessService<ENUMERATION, DAO> implements AbstractEnumerationBusiness<ENUMERATION> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2383681421917333298L;

	public AbstractEnumerationBusinessImpl(DAO dao) {
        super(dao);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public ENUMERATION find(String code){
        return dao.read(code);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public ENUMERATION load(String code) {
    	ENUMERATION enumeration = find(code);
    	load(enumeration);
    	return enumeration;
    }
    
    protected String computeCode(String string){
    	return StringUtils.remove(string, Constant.CHARACTER_SPACE);
    }

}
