package org.cyk.system.root.business.api;

import java.util.List;

import org.cyk.system.root.model.AbstractEnumeration;

public interface AbstractEnumerationBusiness<ENUMERATION extends AbstractEnumeration> extends TypedBusiness<ENUMERATION> {

	ENUMERATION instanciate(String name);
	ENUMERATION instanciate(String code,String name);
	ENUMERATION instanciate(List<String> arguments);
	List<ENUMERATION> instanciateMany(List<List<String>> arguments);
	
	ENUMERATION instanciate(String[] arguments);
	List<ENUMERATION> instanciateMany(String[][] arguments);
	
    ENUMERATION find(String code);
    ENUMERATION load(String code);
    
    /**/
   
}
