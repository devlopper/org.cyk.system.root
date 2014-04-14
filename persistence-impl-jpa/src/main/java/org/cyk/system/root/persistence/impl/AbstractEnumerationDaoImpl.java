package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public abstract class AbstractEnumerationDaoImpl<ENUMERATION extends AbstractEnumeration> extends AbstractTypedDao<ENUMERATION> implements AbstractEnumerationDao<ENUMERATION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;


}
