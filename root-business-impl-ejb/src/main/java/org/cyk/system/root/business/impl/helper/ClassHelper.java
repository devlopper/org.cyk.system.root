package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;

import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;

public class ClassHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public static class Instanciation extends org.cyk.utility.common.helper.ClassHelper.Instanciation.Get.ResultMethod implements Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		protected java.lang.Object __execute__() {
			if(org.cyk.utility.common.helper.ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, (Class<?>) getInput()))
				return inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)getInput()).instanciateOne();
			return super.__execute__();
		}
		
	}
}
