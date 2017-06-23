package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuRenderTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuRenderType;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceMenuRenderTypeDao;

public class UserInterfaceMenuRenderTypeBusinessImpl extends AbstractEnumerationBusinessImpl<UserInterfaceMenuRenderType, UserInterfaceMenuRenderTypeDao> implements UserInterfaceMenuRenderTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserInterfaceMenuRenderTypeBusinessImpl(UserInterfaceMenuRenderTypeDao dao) {
		super(dao); 
	}
	
}
