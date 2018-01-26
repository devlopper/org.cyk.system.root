package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;
import org.cyk.utility.common.ObjectFieldValues;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractEnumerationBusinessImpl<ENUMERATION extends AbstractEnumeration,DAO extends AbstractEnumerationDao<ENUMERATION>> 
    extends AbstractTypedBusinessService<ENUMERATION, DAO> implements AbstractEnumerationBusiness<ENUMERATION> {

	private static final long serialVersionUID = -2383681421917333298L;

	public AbstractEnumerationBusinessImpl(DAO dao) {
        super(dao);
    }
	
	protected ENUMERATION __instanciateOne__(ObjectFieldValues objectFieldValues){
		//return ClassHelper.getInstance().instanciateOne(clazz);
		return commonUtils.instanciateOne(clazz, objectFieldValues);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciateOne(String code,String name) {
		ObjectFieldValues objectFieldValues = new ObjectFieldValues(clazz);
		objectFieldValues.setBaseName(AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER);
		objectFieldValues.set(GlobalIdentifier.FIELD_CODE, code);
		objectFieldValues.set(GlobalIdentifier.FIELD_NAME, name);
		return __instanciateOne__(objectFieldValues);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciateOne(String name) {
		return instanciateOne(RootConstant.Code.generateFromString(name), name);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciateOne() {
		return instanciateOne((String)null, (String)null);
	}
	
    @Override
	protected ENUMERATION __instanciateOne__(String[] values, InstanciateOneListener<ENUMERATION> listener) {
    	ENUMERATION enumeration = listener.getInstance();
    	enumeration.getGlobalIdentifierCreateIfNull();
    	set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE);
    	set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_NAME);
    	set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_ABBREVIATION);
    	set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_ORDER_NUMBER);
    	set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_WEIGHT);
    	set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_DEFAULTED);
		return enumeration;
	}

	protected Integer getInstanciateOneEnumerationStartIndex(String[] values){
    	return 0;
    }
	
	@Getter @Setter
	public static class Details<T extends AbstractEnumeration> extends AbstractOutputDetails<T> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		public Details(T identifiable) {
			super(identifiable);
		}
		
	}
	
	public static class BuilderOneDimensionArray<T extends AbstractEnumeration> extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<T> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray(Class<T> outputClass) {
			super(outputClass);
			addFieldCodeName();
		}
		
		
	}

}
