package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.security.UserAccountLockBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.security.UserAccountLock;
import org.cyk.system.root.persistence.api.security.UserAccountLockDao;

public class UserAccountLockBusinessImpl extends AbstractTypedBusinessService<UserAccountLock, UserAccountLockDao> implements UserAccountLockBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserAccountLockBusinessImpl(UserAccountLockDao dao) {
		super(dao); 
	}

	

	
}
