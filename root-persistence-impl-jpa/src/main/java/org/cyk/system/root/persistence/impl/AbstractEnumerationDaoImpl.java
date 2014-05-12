package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public abstract class AbstractEnumerationDaoImpl<ENUMERATION extends AbstractEnumeration> extends AbstractTypedDao<ENUMERATION> implements AbstractEnumerationDao<ENUMERATION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByCode;
	
	@Override
	protected void namedQueriesInitialisation() {
	    super.namedQueriesInitialisation();
	    registerNamedQuery(readByCode, _select().where("code"));
	}
	
	@Override
	public ENUMERATION read(String code) {
	    return namedQuery(readByCode).parameter("code", code).nullable().resultOne();
	}
}
