package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.InstanceHelper.Lookup.Source;

@SuppressWarnings("unchecked")
public class InstanciateAdapter extends ClassHelper.Instanciation.Get.Adapter.Default<Object> implements Serializable {
	private static final long serialVersionUID = 1L;

	static{
		ClassHelper.Instanciation.Get.CLASSES.add(InstanciateAdapter.class);
		InstanceHelper.Lookup.Source.Adapter.Default.RESULT_METHOD_CLASS = (Class<org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod<Object, Source<?, ?>>>) ClassHelper.getInstance().getByName(Lookup.class);
	}
	
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
}
