package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.validation.Client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor  
public class UserAccountSecretQuestion extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -23914558440705885L;

	@ManyToOne @NotNull private UserAccount userAccount;
	
	@ManyToOne @NotNull(groups=Client.class) private SecretQuestion question;
	
	@NotNull(groups=Client.class) private String answer;
	
}
