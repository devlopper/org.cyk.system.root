package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.security.License;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class Application extends Party implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	@OneToOne private SmtpProperties smtpProperties;
	
	@OneToOne private License license;
	
	private Boolean uniformResourceLocatorFilteringEnabled = Boolean.FALSE;
	
	private String webContext;
}
