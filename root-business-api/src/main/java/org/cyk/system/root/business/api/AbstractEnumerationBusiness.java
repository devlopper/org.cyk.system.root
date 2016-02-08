package org.cyk.system.root.business.api;

import java.util.List;

import org.cyk.system.root.model.AbstractEnumeration;

public interface AbstractEnumerationBusiness<ENUMERATION extends AbstractEnumeration> extends TypedBusiness<ENUMERATION> {

	ENUMERATION instanciateOne(String name);
	ENUMERATION instanciateOne(String code,String name);
	List<ENUMERATION> instanciateMany(List<List<String>> strings);
	List<ENUMERATION> instanciateMany(String[][] strings);
	
    ENUMERATION find(String code);
    ENUMERATION load(String code);
    
    /**/
   
}
