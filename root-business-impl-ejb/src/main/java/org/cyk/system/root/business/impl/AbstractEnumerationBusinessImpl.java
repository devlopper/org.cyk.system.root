package org.cyk.system.root.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;
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
		objectFieldValues.setBaseName(AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER);
		objectFieldValues.set(GlobalIdentifier.FIELD_CODE, code);
		objectFieldValues.set(GlobalIdentifier.FIELD_NAME, name);
		return commonUtils.instanciateOne(clazz, objectFieldValues);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciateOne(String name) {
		return instanciateOne(RootConstant.Code.generateFromString(name), name);
	}
	
	@Override
	public List<ENUMERATION> instanciateMany(String[][] strings) {
		List<List<String>> argumentList = new ArrayList<>();
		for(String[] inputtedArgument : strings){
			List<String> arguments = new ArrayList<>();
			argumentList.add(arguments);
			for(String argument : inputtedArgument)
				arguments.add(argument);
		}
		return null;// instanciateMany(argumentList);
	}

    @Override
	public ENUMERATION instanciateOne(String[] values) {
    	ENUMERATION enumeration = instanciateOne();
    	Integer index = getInstanciateOneEnumerationStartIndex(values);
    	enumeration.setCode(values[index++]);
    	enumeration.setName(values[index++]);
		return enumeration;
	}
    
    protected Integer getInstanciateOneEnumerationStartIndex(String[] values){
    	return 0;
    }

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public ENUMERATION load(String code) {
    	ENUMERATION enumeration = findByGlobalIdentifierCode(code);
    	load(enumeration);
    	return enumeration;
    }
    
}
