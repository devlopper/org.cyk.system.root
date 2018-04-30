package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS)
public class Application extends Party implements Serializable {
	private static final long serialVersionUID = -3099832512046879464L;
	
	@ManyToOne @JoinColumn(name=COLUMN_SMTP_PROPERTIES) private SmtpProperties smtpProperties;	
	private Boolean persistedMenuEnabled = Boolean.FALSE;
	private Boolean uniformResourceLocatorFiltered = Boolean.FALSE;
	private String webContext;
	
	/**/
	
	public static final String FIELD_SMTP_PROPERTIES = "smtpProperties";
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR_FILTERED = "uniformResourceLocatorFiltered";
	public static final String FIELD_PERSISTED_MENU_ENABLED = "persistedMenuEnabled";
	public static final String FIELD_WEB_CONTEXT = "webContext";
	
	public static final String COLUMN_SMTP_PROPERTIES = FIELD_SMTP_PROPERTIES;
}
