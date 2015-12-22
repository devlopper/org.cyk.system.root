package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.Set;

import org.cyk.system.root.model.security.Installation;
import org.cyk.utility.common.cdi.AbstractBean;

public interface BusinessLayerListener {

	void beforeInstall(BusinessLayer businessLayer,Installation installation);
	void afterInstall(BusinessLayer businessLayer,Installation installation);
	
	void handleObjectToInstall(BusinessLayer businessLayer,Object object);
	
	/**/
	
	public static class Adapter extends AbstractBean implements Serializable, BusinessLayerListener {

		private static final long serialVersionUID = -3142367274228861058L;

		@Override
		public void beforeInstall(BusinessLayer businessLayer, Installation installation) {}

		@Override
		public void afterInstall(BusinessLayer businessLayer, Installation installation) {}

		@Override
		public void handleObjectToInstall(BusinessLayer businessLayer,Object object) {}

		
		/**/
		
		public static class Default extends Adapter implements Serializable {

			private static final long serialVersionUID = 8396655646771082967L;
			
		}
	}

}
