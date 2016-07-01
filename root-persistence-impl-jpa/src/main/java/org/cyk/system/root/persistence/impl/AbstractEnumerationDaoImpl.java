package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public abstract class AbstractEnumerationDaoImpl<ENUMERATION extends AbstractEnumeration> extends AbstractTypedDao<ENUMERATION> implements AbstractEnumerationDao<ENUMERATION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByCode,readByCodes;
	
	@Override
	protected void namedQueriesInitialisation() {
	    super.namedQueriesInitialisation();
	    registerNamedQuery(readByCode, _select().where(AbstractEnumeration.FIELD_CODE));
	    registerNamedQuery(readByCodes, "SELECT r FROM "+clazz.getSimpleName()+" r WHERE r.code IN :identifiers");
	}
	
	@Override
	public ENUMERATION read(String code) {
	    return namedQuery(readByCode).parameter(AbstractEnumeration.FIELD_CODE, code).nullable().resultOne();
	}
	
	@Override
	public Collection<ENUMERATION> read(Collection<String> codes) {
		return namedQuery(readByCodes).parameter(QueryStringBuilder.VAR_IDENTIFIERS, codes).resultMany();
	}
}
