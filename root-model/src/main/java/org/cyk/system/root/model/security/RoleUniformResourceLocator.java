package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;

@Entity @Getter @Setter @NoArgsConstructor
public class RoleUniformResourceLocator extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@ManyToOne private Role role;
	
	@ManyToOne private UniformResourceLocator uniformResourceLocator;
	
	public RoleUniformResourceLocator(Role role,UniformResourceLocator uniformResourceLocator) {
		super();
		this.role = role;
		this.uniformResourceLocator = uniformResourceLocator;
	}
	
	@Override
	public String toString() {
		return role+">"+uniformResourceLocator;
	}
	
	/**/
	
	public static final String FIELD_ROLE = "role";
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
}
