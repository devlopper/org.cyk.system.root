package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.InstanceHelper;

@SuppressWarnings("unchecked")
public class InstanciateAdapter extends ClassHelper.Instanciation.Get.Adapter.Default<Object> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__() {
		if(getInput() instanceof AbstractIdentifiable)
			return inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)getInput()).instanciateOne();
		return super.__execute__();
	}
	
	/**/
	
	public static class Lookup extends InstanceHelper.Lookup.Source.Adapter.Default.ResultMethod {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected java.lang.Object __execute__() {
			if(AbstractIdentifiable.class.isAssignableFrom(getOutputClass())){
				AbstractIdentifiable identifiable =  inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(getOutputClass().getName()))
						.read((java.lang.String)getInput());
				return identifiable;
			}
			return super.__execute__();
		}
	}
	
	public static class Pool extends InstanceHelper.Pool.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		@Override
		public <T> Collection<T> load(Class<T> aClass) {
			if(AbstractIdentifiable.class.isAssignableFrom(aClass))
				return (Collection<T>) inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(aClass)).readAll();
			return super.load(aClass);
		}
		
		public <T extends Object> T get(java.lang.Class<T> aClass, Object identifier) {
			if(AbstractIdentifiable.class.isAssignableFrom(aClass))
				return (T) inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(aClass)).read((String)identifier);
			return super.get(aClass, identifier);
		}
	}
}
