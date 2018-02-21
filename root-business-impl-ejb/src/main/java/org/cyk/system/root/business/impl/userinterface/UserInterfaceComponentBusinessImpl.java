package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.UserInterfaceComponentBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.userinterface.UserInterfaceComponent;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceComponentDao;

public class UserInterfaceComponentBusinessImpl extends AbstractTypedBusinessService<UserInterfaceComponent, UserInterfaceComponentDao> implements UserInterfaceComponentBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserInterfaceComponentBusinessImpl(UserInterfaceComponentDao dao) {
		super(dao); 
	}
		
}