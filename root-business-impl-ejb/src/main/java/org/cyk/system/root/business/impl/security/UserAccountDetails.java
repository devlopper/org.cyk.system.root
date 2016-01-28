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
	
	@Input @InputText private String names,username,roles,disabled;
	
	public UserAccountDetails(UserAccount userAccount) {
		super(userAccount);
		if(userAccount.getUser() instanceof Person)
			names = ((Person)userAccount.getUser()).getNames();
		else if(userAccount.getUser() instanceof Application)
			names = ((Application)userAccount.getUser()).getName();
		username = userAccount.getCredentials().getUsername();
		roles = StringUtils.join(userAccount.getRoles(),Constant.CHARACTER_COMA);
		disabled = formatResponse(Boolean.TRUE.equals(userAccount.getDisabled()));
	}
}