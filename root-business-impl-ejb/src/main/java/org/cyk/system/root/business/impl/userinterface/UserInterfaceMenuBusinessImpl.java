package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuBusiness;
import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceMenuDao;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceMenuItemDao;

public class UserInterfaceMenuBusinessImpl extends AbstractCollectionBusinessImpl<UserInterfaceMenu, UserInterfaceMenuItem,UserInterfaceMenuDao,UserInterfaceMenuItemDao,UserInterfaceMenuItemBusiness> implements UserInterfaceMenuBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserInterfaceMenuBusinessImpl(UserInterfaceMenuDao dao) {
		super(dao); 
	}
	
	@Override
	protected Object[] getPropertyValueTokens(UserInterfaceMenu userInterfaceMenu, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE}, name)){
			return new Object[]{RandomStringUtils.randomAlphabetic(5)};
		}
		return super.getPropertyValueTokens(userInterfaceMenu, name);
	}
	
}
