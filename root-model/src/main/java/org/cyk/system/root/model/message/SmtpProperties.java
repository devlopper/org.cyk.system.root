package org.cyk.system.root.model.message;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.Credentials;
import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
public class SmtpProperties extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -5516162693913912313L;

	@Column(name="csender",nullable=false) @NotNull  @Email
	private String from;
	
	@OneToOne(cascade=CascadeType.ALL) private Credentials credentials;
	
	@Embedded private SmtpSocketFactory socketFactory = new SmtpSocketFactory();
	
	@Column(name="chost",nullable=false) @NotNull
	private String host;
	
	@Column(name="cport",nullable=false) @NotNull 
	private Integer port;
	
	@Column(nullable=false) @NotNull 
	private Boolean authenticated = Boolean.TRUE,secured = Boolean.FALSE;
	
}
