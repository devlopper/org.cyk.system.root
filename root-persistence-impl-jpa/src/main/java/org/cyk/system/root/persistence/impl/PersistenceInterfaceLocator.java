package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.utility.common.ClassLocator;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class PersistenceInterfaceLocator extends ClassLocator implements Serializable {

	private static final long serialVersionUID = -3187769614985951029L;

	private static PersistenceInterfaceLocator INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		setClassType("Persistence");
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
				return new String[]{"persistence.api"};
			}
			@Override
			protected String[] getModuleSuffixes() {
				return new String[]{"Dao"};
			}
			
		});
		getClassLocatorListeners().add(listener);
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> TypedDao<T> injectTyped(Class<T> aClass) {
		TypedDao<T> dao = (TypedDao<T>) super.injectLocated(aClass);
		if(dao == null)
			logWarning("Persistence not yet define for class {}", aClass);
		return dao;
	}
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> TypedDao<T> injectTypedByObject(T object) {
		return (TypedDao<T>) injectTyped(object.getClass());
	}
	
	public static PersistenceInterfaceLocator getInstance() {
		return INSTANCE;
	}
}
