package org.cyk.system.root.persistence.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;

public class Utils {
	
	public Set<String> componentNames(Class<? extends AbstractIdentifiable> aClass){
		Set<String> set = new HashSet<>();
		set.add(aClass.getName());
		if(StringUtils.contains(aClass.getName(), ".model.")){
			set.add(StringUtils.replaceOnce(aClass.getName(),".model.",".persistence.api.")+"Dao");
			set.add(StringUtils.replaceOnce(aClass.getName(),".model.",".persistence.impl.")+"DaoImpl");
		}
		return set;
	}

}
