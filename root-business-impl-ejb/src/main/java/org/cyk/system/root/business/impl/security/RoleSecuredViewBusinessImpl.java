package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.security.RoleSecuredViewBusiness;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleSecuredView;
import org.cyk.system.root.persistence.api.security.RoleSecuredViewDao;

public class RoleSecuredViewBusinessImpl extends AbstractSecuredViewBusinessImpl<RoleSecuredView, RoleSecuredViewDao,Role> implements RoleSecuredViewBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public RoleSecuredViewBusinessImpl(RoleSecuredViewDao dao) {
		super(dao); 
	}
	
}
