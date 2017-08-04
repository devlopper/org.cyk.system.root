package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.ClassLocator;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class ModelClassLocator extends ClassLocator implements Serializable {

	private static final long serialVersionUID = -3187769614985951029L;

	private static ModelClassLocator INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		setClassType("Model");
		Listener listener = new Listener.Adapter(){
			private static final long serialVersionUID = -979036256355287919L;

			@Override
			public Boolean isLocatable(Class<?> basedClass) {
				return AbstractIdentifiableBusinessServiceImpl.class.isAssignableFrom(basedClass);
			}
		};
		
		listener.setGetNameMethod(new Listener.AbstractGetOrgCykSystem() {
			private static final long serialVersionUID = -7213562588417233353L;
			@Override
			protected String getBaseClassPackageName() {
				return "business.impl";
			}
			@Override
			protected String getModulePrefix() {
				return "model";
			}
			@Override
			protected String getModuleSuffix() {
				return "";
			}
			
		});
		getClassLocatorListeners().add(listener);
	}
	
	public static ModelClassLocator getInstance() {
		return INSTANCE;
	}
}
