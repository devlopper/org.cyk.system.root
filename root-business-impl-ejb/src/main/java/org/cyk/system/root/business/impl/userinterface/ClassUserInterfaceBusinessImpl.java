package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.cyk.system.root.business.api.userinterface.ClassUserInterfaceBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessServiceImpl;
import org.cyk.system.root.model.userinterface.ClassUserInterface;

@Singleton
public class ClassUserInterfaceBusinessImpl extends AbstractBusinessServiceImpl implements ClassUserInterfaceBusiness,Serializable {

	private static final long serialVersionUID = 7387144022390774064L;

	private static final Map<Class<?>, ClassUserInterface> CLASS_USER_INTERFACE_MAP = new HashMap<>();
	
	@Override
	public ClassUserInterface findByClass(Class<?> aClass) {
		return CLASS_USER_INTERFACE_MAP.get(aClass);
	}

	@Override
	public void register(ClassUserInterface classUserInterface) {
		CLASS_USER_INTERFACE_MAP.put(classUserInterface.getClazz(), classUserInterface);
	}
	
}
