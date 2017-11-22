package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.ClassLocator;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class BusinessInterfaceLocator extends ClassLocator implements Serializable {

	private static final long serialVersionUID = -3187769614985951029L;

	private static BusinessInterfaceLocator INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		setClassType("Business");
		Listener listener = new Listener.Adapter(){
			private static final long serialVersionUID = -979036256355287919L;

			@Override
			public Boolean isLocatable(Class<?> basedClass) {
				return AbstractIdentifiable.class.isAssignableFrom(basedClass);
			}
		};
		
		listener.setGetNameMethod(new Listener.AbstractGetOrgCykSystem() {
			private static final long serialVersionUID = -7213562588417233353L;
			@Override
			protected String getBaseClassPackageName() {
				return "model";
			}
			@Override
			protected String[] getModulePrefixes() {
				return new String[]{"business.api"};
			}
			@Override
			protected String[] getModuleSuffixes() {
				return new String[]{"Business"};
			}
			
		});
		getClassLocatorListeners().add(listener);
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> TypedBusiness<T> injectTyped(Class<T> aClass) {
		return (TypedBusiness<T>) super.injectLocated(aClass);
	}
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> TypedBusiness<T> injectTypedByObject(T object) {
		return (TypedBusiness<T>) injectTyped(object.getClass());
	}
	
	public static BusinessInterfaceLocator getInstance() {
		return INSTANCE;
	}
}
