package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractEnumeration;

public interface AbstractEnumerationDao<ENUMERATION extends AbstractEnumeration> extends TypedDao<ENUMERATION> {

	Collection<ENUMERATION> readByParent(ENUMERATION parent);
	Long countByParent(ENUMERATION parent);

}
