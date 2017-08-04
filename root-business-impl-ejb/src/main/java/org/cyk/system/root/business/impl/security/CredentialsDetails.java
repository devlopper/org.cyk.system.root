package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CredentialsDetails extends AbstractOutputDetails<Credentials> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue software;
	@Input @InputText private String username,password;
	
	public CredentialsDetails(Credentials credentials) {
		super(credentials);
		if(credentials!=null){
			software = new FieldValue(credentials.getSoftware());
			username = credentials.getUsername();
			password = credentials.getPassword();
		}
	}
	
	public static final String FIELD_SOFTWARE = "software";
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_PASSWORD = "password";
}