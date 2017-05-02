package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS) 
public class License extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@OneToOne
	/**
	 * Readable human text on which agreement is made
	 */
	private File file;
	
	/**
	 * The license key. used for mapping in the license management system
	 */
	@Column(name="licence_key")
	private String key;
	
	@Embedded @Valid
	private Period period = new Period();
	
	@NotNull(groups=org.cyk.utility.common.validation.System.class)
	
	/**
	 * to track who has been activated on the license management system
	 */
	private Boolean activated = Boolean.FALSE;
	
	@NotNull
	/**
	 * 
	 */
	private Boolean expirable = Boolean.FALSE;
	
	@NotNull
	/**
	 * 
	 */
	private Boolean expired = Boolean.FALSE;
	
	@Override
	public String getUiString() {
		return identifier+Constant.EMPTY_STRING;
	}
}
