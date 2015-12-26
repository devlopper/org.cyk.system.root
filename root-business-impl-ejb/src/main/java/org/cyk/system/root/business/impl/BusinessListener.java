package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.computation.DataReadConfiguration;

public interface BusinessListener {

	<T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass,DataReadConfiguration configuration);
	
	<T extends AbstractIdentifiable> Long count(Class<T> dataClass, DataReadConfiguration configuration);
	
	Collection<BusinessListener> LISTENERS = new ArrayList<>();
	
	/**/
	
	public static class Adapter extends BeanAdapter implements BusinessListener,Serializable {
		private static final long serialVersionUID = -6980159194301961222L;

		@Override
		public <T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass, DataReadConfiguration configuration) {
			return null;
		}

		@Override
		public <T extends AbstractIdentifiable> Long count(Class<T> dataClass, DataReadConfiguration configuration) {
			return null;
		}

		/**/
		
		public static class Default extends Adapter implements BusinessListener,Serializable {
			private static final long serialVersionUID = -7111137502842639297L;
			@Override
			public <T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass, DataReadConfiguration configuration) {
				TypedBusiness<T> business = getBusiness(dataClass);
				if(business==null)
					return null;
				else
					return business.findAll();
			}

			@Override
			public <T extends AbstractIdentifiable> Long count(Class<T> dataClass, DataReadConfiguration configuration) {
				TypedBusiness<T> business = getBusiness(dataClass);
				if(business==null)
					return null;
				else
					return business.countAll();
			}
			
			@SuppressWarnings("unchecked")
			protected <T extends AbstractIdentifiable> TypedBusiness<T> getBusiness(Class<T> dataClass){
				return (TypedBusiness<T>) BusinessLocator.getInstance().locate(dataClass);
			}
		}

	}

}