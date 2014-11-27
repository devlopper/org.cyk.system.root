package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.validation.Client;

@Entity
@Getter @Setter @NoArgsConstructor
public class SecretQuestionAnswer extends AbstractIdentifiable implements Serializable {

	
	private static final long serialVersionUID = -1426919647717880937L;
	
	@ManyToOne 
	@NotNull(groups=Client.class)
	private SecretQuestion question;
	
	@NotNull(groups=Client.class)
	private String answer;
	
}
