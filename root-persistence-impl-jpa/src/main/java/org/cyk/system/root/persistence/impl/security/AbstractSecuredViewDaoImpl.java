package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;

import org.cyk.system.root.model.security.AbstractSecuredView;
import org.cyk.system.root.persistence.api.security.AbstractSecuredViewDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractSecuredViewDaoImpl<VIEW extends AbstractSecuredView<ACCESSOR>,ACCESSOR> extends AbstractTypedDao<VIEW> implements AbstractSecuredViewDao<VIEW,ACCESSOR>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	
}
