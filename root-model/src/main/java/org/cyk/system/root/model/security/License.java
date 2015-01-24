package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;

@Entity
@Getter @Setter @NoArgsConstructor
public class License extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@Column(name="licence_text",length=1024*4)
	@NotNull(groups=org.cyk.utility.common.validation.System.class)
	/**
	 * Readable human text on which agreement is made
	 */
	private String text;
	
	@Column(name="licence_key")
	//@Input
	//@InputText
	private String key;
	
	@IncludeInputs
	@OneToOne 
	@Valid
	private Period period = new Period();
	
	@NotNull(groups=org.cyk.utility.common.validation.System.class)
	/**
	 * to track who has been activated on the license management system
	 */
	private Boolean activated;
	
	@NotNull(groups=org.cyk.utility.common.validation.System.class)
	/**
	 * 
	 */
	private Boolean expired;
	
}
