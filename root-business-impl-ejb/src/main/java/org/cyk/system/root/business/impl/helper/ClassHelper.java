package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;

import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;

public class ClassHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public static class Instanciation extends org.cyk.utility.common.helper.ClassHelper.Instanciation.Get.Adapter.Default<Object> implements Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		protected Object __execute__() {
			if(getInput() instanceof AbstractIdentifiable)
				return inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)getInput()).instanciateOne();
			return super.__execute__();
		}
		
	}
}
