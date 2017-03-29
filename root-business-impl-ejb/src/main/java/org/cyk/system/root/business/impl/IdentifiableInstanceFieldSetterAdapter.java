package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.system.root.persistence.impl.Utils;

public class IdentifiableInstanceFieldSetterAdapter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static class OneDimensionObjectArray<T extends AbstractIdentifiable> extends org.cyk.utility.common.accessor.InstanceFieldSetter.OneDimensionObjectArray.Adapter.Default<T> implements Serializable {

		private static final long serialVersionUID = 1L;

		public OneDimensionObjectArray(Class<T> outputClass) {
			super(outputClass);
		}
		
		@Override
		public Object getKeyType() {
			if(getFieldNames().contains(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_IDENTIFIER)))
				return KEY_TYPE_GLOBAL;
			if(getFieldNames().contains(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE)))
				return KEY_TYPE_LOCAL;
			return super.getKeyType();
		}
		
		@Override
		public Object getKeyType(Object[] values) {
			return getKeyType();
		}
		
		@Override
		public Integer getKeyIndex() {
			for(String fieldName : new String[]{commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_IDENTIFIER)
					,commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE)}){
				if(getFieldNames().contains(fieldName))
					return getFieldNamesIndexes().get(fieldName);
			}
			return super.getKeyIndex();
		}
		
		@Override
		public Integer getKeyIndex(Object[] values) {
			return getKeyIndex();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Object getValue(Class<?> fieldType, Object value) {
			if(fieldType.isAnnotationPresent(Entity.class)){
				TypedDao<AbstractIdentifiable> persistence = inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)fieldType);
				if(Boolean.TRUE.equals(isReadByGlobalIdentifierValue(fieldType, value)))
					return persistence.readByGlobalIdentifierValue((String) value);
				if(Boolean.TRUE.equals(isReadByGlobalIdentifierCode(fieldType, value)))
					return persistence.read((String)value);
			}
			return super.getValue(fieldType, value);
		}
	
		protected Boolean isReadByGlobalIdentifierValue(Class<?> fieldType, Object value){
			if(File.class.equals(fieldType))
				return Boolean.TRUE;
			return Boolean.FALSE;
		}
		
		protected Boolean isReadByGlobalIdentifierCode(Class<?> fieldType, Object value){
			return Boolean.TRUE;
		}
		
		/**/
		
		
		
	}
	
	public static class TwoDimensionObjectArray<T extends AbstractIdentifiable> extends org.cyk.utility.common.accessor.InstanceFieldSetter.TwoDimensionObjectArray.Adapter.Default<T> implements Serializable {

		private static final long serialVersionUID = 1L;

		public TwoDimensionObjectArray(OneDimensionObjectArray<T> oneDimension) {
			super(oneDimension);
			TypedDao<T> persistence = inject(PersistenceInterfaceLocator.class).injectTyped(getOneDimension().getOutputClass());
			Object keyType = oneDimension.getKeyType();
			if(keyType != null){
				existingKeys.clear();
				for(String code : OneDimensionObjectArray.KEY_TYPE_LOCAL.equals(keyType) ? Utils.getCodes(persistence.readAll()) 
						: Utils.getGlobalIdentfierValues( Utils.getGlobalIdentfiers(persistence.readAll())))
					existingKeys.add(code);	
			}
			
		}
		
		@Override
		public T instanciate(Object[] values) {
			return inject(BusinessInterfaceLocator.class).injectTyped(getOneDimension().getOutputClass()).instanciateOne();
		}
		
		@Override
		public T getInstanceByKey(Object[] values, Object key,Object type) {
			TypedDao<T> persistence = inject(PersistenceInterfaceLocator.class).injectTyped(getOneDimension().getOutputClass());
			if(OneDimensionObjectArray.KEY_TYPE_LOCAL.equals(type))
				return (T) persistence.read((String)key);
			if(OneDimensionObjectArray.KEY_TYPE_GLOBAL.equals(type))
				return (T) persistence.readByGlobalIdentifierValue((String)key);
			return null;
		}
		
	}
	
}
