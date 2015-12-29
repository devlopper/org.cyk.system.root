package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;

@Entity @Getter @Setter @NoArgsConstructor
public class RoleUniformResourceLocator extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@ManyToOne @NotNull private Role role;
	
	@ManyToOne @NotNull private UniformResourceLocator uniformResourceLocator;
	
	public RoleUniformResourceLocator(Role role,UniformResourceLocator uniformResourceLocator) {
		super();
		this.role = role;
		this.uniformResourceLocator = uniformResourceLocator;
	}
	
	@Override
	public String toString() {
		return uniformResourceLocator.getAddress();
	}
	
	@Override
	public int hashCode() {
		if(identifier!=null)
			return identifier.hashCode();
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime
				* result
				+ ((uniformResourceLocator == null) ? 0
						: uniformResourceLocator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleUniformResourceLocator other = (RoleUniformResourceLocator) obj;
		if(identifier!=null && other.identifier!=null)
			return identifier.equals(other.identifier);
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (uniformResourceLocator == null) {
			if (other.uniformResourceLocator != null)
				return false;
		} else if (!uniformResourceLocator.equals(other.uniformResourceLocator))
			return false;
		return true;
	}

	/**/

	public static final String FIELD_ROLE = "role";
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
}
