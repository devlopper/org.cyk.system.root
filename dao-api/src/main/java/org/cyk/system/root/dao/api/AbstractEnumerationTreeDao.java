package org.cyk.system.root.dao.api;

import java.util.Collection;

import org.cyk.system.root.model.EnumerationTree;

public interface AbstractEnumerationTreeDao<ENUMERATION extends EnumerationTree> extends TypedDao<ENUMERATION> {

	Collection<ENUMERATION> readByParent(ENUMERATION parent);
	
	Collection<ENUMERATION> readByType(EnumerationTree type);
	
	Collection<ENUMERATION> readByParentByType(ENUMERATION parent,EnumerationTree type);
	
}
