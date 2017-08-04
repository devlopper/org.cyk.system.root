package org.cyk.system.root.business.api;

import org.apache.commons.lang3.ArrayUtils;

public enum Crud {
    CREATE,READ,UPDATE,DELETE
    ;
	
	public static Boolean isCreateOrUpdate(Crud crud){
		return ArrayUtils.contains(new Crud[]{Crud.CREATE, Crud.UPDATE},crud);
	}
}
