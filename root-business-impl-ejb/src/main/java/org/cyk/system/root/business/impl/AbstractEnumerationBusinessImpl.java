package org.cyk.system.root.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ObjectFieldValues;

public abstract class AbstractEnumerationBusinessImpl<ENUMERATION extends AbstractEnumeration,DAO extends AbstractEnumerationDao<ENUMERATION>> 
    extends AbstractTypedBusinessService<ENUMERATION, DAO> implements AbstractEnumerationBusiness<ENUMERATION> {

	private static final long serialVersionUID = -2383681421917333298L;

	public AbstractEnumerationBusinessImpl(DAO dao) {
        super(dao);
    }
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciateOne(String code,String name) {
		ObjectFieldValues objectFieldValues = new ObjectFieldValues(clazz);
		objectFieldValues.set(AbstractEnumeration.FIELD_CODE, code);
		objectFieldValues.set(AbstractEnumeration.FIELD_NAME, name);
		return commonUtils.instanciateOne(clazz, objectFieldValues);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciateOne(String name) {
		return instanciateOne(computeCode(name), name);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ENUMERATION> instanciateMany(List<List<String>> arguments) {
		List<ENUMERATION> r = new ArrayList<>();
		for(List<String> argument : arguments){
			if(argument.size()==1)
				r.add(instanciateOne(argument.get(0)));
			else if(argument.size()==2)
				r.add(instanciateOne(argument.get(0),argument.get(1)));
			else
				throw new RuntimeException("Too much arguments") ;
		}
		return r;
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
