package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class UserAccountDetails extends AbstractOutputDetails<UserAccount> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String user,username,roles,disabled,currentLock;
	
	public UserAccountDetails(UserAccount userAccount) {
		super(userAccount);
		if(userAccount.getUser() instanceof Person)
			user = formatUsingBusiness((Person)userAccount.getUser());
		else if(userAccount.getUser() instanceof Application)
			user = ((Application)userAccount.getUser()).getName();
		username = userAccount.getCredentials().getUsername();
		roles = StringUtils.join(userAccount.getRoles(),Constant.CHARACTER_COMA);
		disabled = formatResponse(Boolean.TRUE.equals(userAccount.getUsable()));
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_ROLES = "roles";
	public static final String FIELD_DISABLED = "disabled";
	public static final String FIELD_CURRENT_LOCK = "currentLock";
}