package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter @Setter @NoArgsConstructor
public class RoleUniformResourceLocator extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@ManyToOne private Role role;
	
	@ManyToOne private UniformResourceLocator uniformResourceLocator;
	
}
