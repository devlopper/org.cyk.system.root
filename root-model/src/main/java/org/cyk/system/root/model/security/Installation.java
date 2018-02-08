package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.person.Person;

@Getter @Setter
public class Installation extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -8934293445475110562L;

	private Application application;
	private Credentials administratorCredentials;
	
	private Person manager;
	private Credentials managerCredentials;
	
	private License license;
	
	private SmtpProperties smtpProperties;
	
	private Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
	
	private Map<String, Set<String>> urlRolesMap = new HashMap<>();
	
	private Boolean isCreateAccounts = Boolean.TRUE;
	private Boolean isCreateLicence = Boolean.TRUE;
	
	@Override
	public String getUiString() {
		return application.getUiString();
	}

}
