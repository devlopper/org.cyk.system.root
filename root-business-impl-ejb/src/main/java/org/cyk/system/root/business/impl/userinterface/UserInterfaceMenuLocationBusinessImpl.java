package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuLocationBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuLocation;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceMenuLocationDao;

public class UserInterfaceMenuLocationBusinessImpl extends AbstractEnumerationBusinessImpl<UserInterfaceMenuLocation, UserInterfaceMenuLocationDao> implements UserInterfaceMenuLocationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserInterfaceMenuLocationBusinessImpl(UserInterfaceMenuLocationDao dao) {
		super(dao); 
	}
	
}
