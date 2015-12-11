package org.cyk.system.root.business.api.userinterface;

import org.cyk.system.root.model.userinterface.ClassUserInterface;

public interface ClassUserInterfaceBusiness {

	ClassUserInterface findByClass(Class<?> aClass);
    
	void register(ClassUserInterface classUserInterface);
	
}
