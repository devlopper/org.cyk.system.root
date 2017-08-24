package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;

public class InstanceHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Listener extends org.cyk.utility.common.helper.InstanceHelper.Listener.Adapter.Default{
    	private static final long serialVersionUID = 1L;
		@Override
    	public Object getIdentifier(Object instance) {
    		if(instance instanceof AbstractIdentifiable)
    			return ((AbstractIdentifiable)instance).getCode();
    		else if(instance instanceof GlobalIdentifier)
    			return ((GlobalIdentifier)instance).getIdentifier();
    		return super.getIdentifier(instance);
    	}
    }
	
	public static class BuilderOneDimensionArray<T extends AbstractIdentifiable> extends org.cyk.utility.common.helper.InstanceHelper.Builder.OneDimensionArray.Adapter.Default<T> implements Serializable{
    	private static final long serialVersionUID = 1L;
    	
    	public BuilderOneDimensionArray(Class<T> outputClass) {
			super(outputClass);
		}
    	
    	public BuilderOneDimensionArray<T> addFieldCode() {
			addParameterArrayElementString(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE));
			return this;
		}
    	
    	public BuilderOneDimensionArray<T> addFieldCodeName() {
    		addFieldCode();
			addParameterArrayElementString(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
			return this;
		}
    	
    	@Override
    	public BuilderOneDimensionArray<T> addParameterArrayElementString(String... strings) {
    		return (BuilderOneDimensionArray<T>) super.addParameterArrayElementString(strings);
    	}
    	
    	@Override
    	public BuilderOneDimensionArray<T> addParameterArrayElementString(Integer index, String string) {
    		return (BuilderOneDimensionArray<T>) super.addParameterArrayElementString(index, string);
    	}
    	
    	@Override
    	public BuilderOneDimensionArray<T> addParameterArrayElementStringIndexInstance(Object... arg0) {
    		return (BuilderOneDimensionArray<T>) super.addParameterArrayElementStringIndexInstance(arg0);
    	}
    	
    	/**/
    	
	}
		
	public static class Lookup extends org.cyk.utility.common.helper.InstanceHelper.Lookup.Source.Adapter.Default.ResultMethod {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected java.lang.Object __execute__(java.lang.Object instance) {
			if(instance==null){
				if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, getOutputClass())){
					@SuppressWarnings("unchecked")
					AbstractIdentifiable identifiable =  inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(getOutputClass().getName()))
							.read((java.lang.String)getInput());
					return identifiable;
				}else{
					
				}
			}
			return super.__execute__(instance);
		}
	}
	
	public static class Pool extends org.cyk.utility.common.helper.InstanceHelper.Pool.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		@Override
		public <T> Collection<T> load(Class<T> aClass) {
			if(AbstractIdentifiable.class.isAssignableFrom(aClass))
				return (Collection<T>) inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(aClass)).readAll();
			return super.load(aClass);
		}
		
		@SuppressWarnings("unchecked")
		public <T extends Object> T get(java.lang.Class<T> aClass, Object identifier) {
			T instance = super.get(aClass, identifier);
			if(instance==null)
				if(AbstractIdentifiable.class.isAssignableFrom(aClass))
					return (T) inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(aClass)).read((String)identifier);
			return instance;
		}
	}
	
}
