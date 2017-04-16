package org.cyk.system.root.model.message;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.Service;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS)
public class SmtpProperties extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -5516162693913912313L;
	
	@ManyToOne @NotNull private Service service;
	
	@Column(name="csender",nullable=false) @NotNull  @Email
	private String from;
	
	@ManyToOne @NotNull private Credentials credentials;
	
	/*@Embedded private SmtpSocketFactory socketFactory = new SmtpSocketFactory();
	
	public SmtpSocketFactory getSocketFactory(){
		if(socketFactory==null)
			socketFactory = new SmtpSocketFactory();
		return socketFactory;
	}*/
	
	@Override
	public String getUiString() {
		return service.getUiString();
	}
	
	public static final String FIELD_SERVICE = "service";
	public static final String FIELD_FROM = "from";
	public static final String FIELD_CREDENTIALS = "credentials";
	public static final String FIELD_SOCKET_FACTORY = "socketFactory";
}
