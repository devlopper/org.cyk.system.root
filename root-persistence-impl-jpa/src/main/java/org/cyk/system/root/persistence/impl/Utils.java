package org.cyk.system.root.persistence.impl;

import java.util.Collection;
import java.util.HashSet;

import org.cyk.system.root.model.AbstractIdentifiable;


public class Utils {
	 
	public static Collection<Long> ids(Collection<? extends AbstractIdentifiable> identifiables){
		Collection<Long> ids = new HashSet<>();
		for(AbstractIdentifiable identifiable : identifiables)
			ids.add(identifiable.getIdentifier());
		return ids;
	}

}
