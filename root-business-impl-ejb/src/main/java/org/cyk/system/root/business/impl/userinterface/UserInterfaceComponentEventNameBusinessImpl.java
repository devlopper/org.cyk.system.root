package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.UserInterfaceComponentEventNameBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.userinterface.UserInterfaceComponentEventName;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceComponentEventNameDao;

public class UserInterfaceComponentEventNameBusinessImpl extends AbstractEnumerationBusinessImpl<UserInterfaceComponentEventName, UserInterfaceComponentEventNameDao> implements UserInterfaceComponentEventNameBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserInterfaceComponentEventNameBusinessImpl(UserInterfaceComponentEventNameDao dao) {
		super(dao); 
	}
	
}
