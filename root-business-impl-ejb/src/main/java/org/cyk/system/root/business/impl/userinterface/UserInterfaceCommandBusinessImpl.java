package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.userinterface.UserInterfaceCommandBusiness;
import org.cyk.system.root.business.api.userinterface.UserInterfaceComponentBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.userinterface.UserInterfaceCommand;
import org.cyk.system.root.model.userinterface.UserInterfaceComponent;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceCommandDao;

public class UserInterfaceCommandBusinessImpl extends AbstractTypedBusinessService<UserInterfaceCommand, UserInterfaceCommandDao> implements UserInterfaceCommandBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UserInterfaceCommandBusinessImpl(UserInterfaceCommandDao dao) {
		super(dao); 
	}
	
	@Override
	protected Object[] getPropertyValueTokens(UserInterfaceCommand userInterfaceCommand, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, name)){
			return new Object[]{userInterfaceCommand.getComponent()};
		}
		return super.getPropertyValueTokens(userInterfaceCommand, name);
	}
	
	@Override
	protected void beforeCreate(UserInterfaceCommand userInterfaceCommand) {
		if(userInterfaceCommand.getComponent()==null && Boolean.TRUE.equals(userInterfaceCommand.getAutomaticallyCreateComponent()) && StringUtils.isNotBlank(userInterfaceCommand.getCode())){
			userInterfaceCommand.setComponent(read(UserInterfaceComponent.class, userInterfaceCommand.getCode()));
			if(userInterfaceCommand.getComponent()==null){
				userInterfaceCommand.setComponent(inject(UserInterfaceComponentBusiness.class).instanciateOne());
				userInterfaceCommand.setAutomaticallyCreateComponent(Boolean.TRUE);
			}
		}
		super.beforeCreate(userInterfaceCommand);
		if(Boolean.TRUE.equals(userInterfaceCommand.getAutomaticallyCreateComponent()))
			createIfNotIdentified(userInterfaceCommand.getComponent());
	}
	
}