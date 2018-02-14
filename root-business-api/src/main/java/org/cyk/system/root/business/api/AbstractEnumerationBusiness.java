package org.cyk.system.root.business.api;

import org.cyk.system.root.model.AbstractEnumeration;

public interface AbstractEnumerationBusiness<ENUMERATION extends AbstractEnumeration> extends TypedBusiness<ENUMERATION> {

	//TODO should be deleted
	ENUMERATION instanciateOne(String name);
	//TODO should be deleted
	ENUMERATION instanciateOne(String code,String name);
	
}
