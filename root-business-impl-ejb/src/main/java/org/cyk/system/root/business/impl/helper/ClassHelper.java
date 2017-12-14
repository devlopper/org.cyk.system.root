package org.cyk.system.root.business.impl.helper;

import java.io.Serializable;

import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.utility.common.cdi.AbstractBean;

public class ClassHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Listener extends org.cyk.utility.common.helper.ClassHelper.Listener.Adapter.Default{
    	private static final long serialVersionUID = 1L;
		
    	@Override
    	public String getIdentifierFieldName(Class<?> aClass) {
    		return "globalIdentifier.code";
    	}
    	
    	@Override
    	public Boolean isHierarchy(Class<?> aClass) {
    		return org.cyk.utility.common.helper.ClassHelper.getInstance().isInstanceOf(AbstractDataTreeNode.class, aClass);
    	}
    	
    }
	
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
