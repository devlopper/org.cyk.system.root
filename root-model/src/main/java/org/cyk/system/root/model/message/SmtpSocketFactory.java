package org.cyk.system.root.model.message;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractModelElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Embeddable
public class SmtpSocketFactory extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 8430989676173921303L;

	@Column(name="socketclass",nullable=false) @NotNull 
	private String clazz;

	@Column(name="socketfallback",nullable=false) @NotNull 
	private Boolean fallback;
	
	@Column(name="socketport",nullable=false) @NotNull 
	private Integer port;
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	public static final String FIELD_CLAZZ = "clazz";
	public static final String FIELD_FALLBACK = "fallback";
	public static final String FIELD_PORT = "port";
	
}
