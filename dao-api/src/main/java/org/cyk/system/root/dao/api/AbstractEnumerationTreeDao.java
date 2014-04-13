package org.cyk.system.root.dao.api;

import java.util.Collection;

import org.cyk.system.root.model.EnumerationTree;

public interface AbstractEnumerationTreeDao<ENUMERATION extends EnumerationTree> extends TypedDao<ENUMERATION> {

	Collection<ENUMERATION> readByParent(ENUMERATION parent);
	Long countByParent(ENUMERATION parent);
	
	Collection<ENUMERATION> readByType(EnumerationTree type);
	Long countByType(EnumerationTree type);
	
	Collection<ENUMERATION> readByParentByType(ENUMERATION parent,EnumerationTree type);
	Long countByParentByType(ENUMERATION parent,EnumerationTree type);
	
}
