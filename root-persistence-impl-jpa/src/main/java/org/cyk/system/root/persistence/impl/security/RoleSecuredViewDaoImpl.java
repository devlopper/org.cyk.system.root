package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;

import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleSecuredView;
import org.cyk.system.root.persistence.api.security.RoleSecuredViewDao;

public class RoleSecuredViewDaoImpl extends AbstractSecuredViewDaoImpl<RoleSecuredView,Role> implements RoleSecuredViewDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
}
 