package org.cyk.system.root.model.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.Person;

@Getter @Setter
public class Installation extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -8934293445475110562L;

	private Application application;
	private Credentials administratorCredentials;
	
	private Person manager;
	private Credentials managerCredentials;
	
	private License license;
	
	@Override
	public String getUiString() {
		return application.getUiString();
	}

}
