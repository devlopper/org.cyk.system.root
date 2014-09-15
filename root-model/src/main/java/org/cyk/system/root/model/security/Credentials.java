package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.validation.Client;

@Entity @Getter @Setter @NoArgsConstructor
public class Credentials extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	@Column(unique=true,nullable=false)
	@NotNull(groups={Client.class,org.cyk.utility.common.validation.System.class})
	private String username;
	
	@Column(nullable=false)
	@NotNull(groups={Client.class,org.cyk.utility.common.validation.System.class})
	private String password;
	
	@Override
	public String toString() {
		return "{"+username+","+password+"}";
	}
	
}
