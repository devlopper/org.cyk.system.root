package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.UserInterfaceComponentEventBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.userinterface.UserInterfaceComponentEvent;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceComponentEventDao;

public class UserInterfaceComponentEventBusinessImpl extends AbstractTypedBusinessService<UserInterfaceComponentEvent, UserInterfaceComponentEventDao> implements UserInterfaceComponentEventBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserInterfaceComponentEventBusinessImpl(UserInterfaceComponentEventDao dao) {
		super(dao); 
	}
		
}