package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuType;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceMenuTypeDao;

public class UserInterfaceMenuTypeBusinessImpl extends AbstractEnumerationBusinessImpl<UserInterfaceMenuType, UserInterfaceMenuTypeDao> implements UserInterfaceMenuTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserInterfaceMenuTypeBusinessImpl(UserInterfaceMenuTypeDao dao) {
		super(dao); 
	}
	
}
