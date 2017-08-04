package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.cyk.system.root.model.security.UserAccount;

@SessionScoped @Getter @Setter @ToString
public class UserSessionBusiness implements Serializable {

	private static final long serialVersionUID = -7218336282129565169L;

	private UserAccount userAccount;
	
}
