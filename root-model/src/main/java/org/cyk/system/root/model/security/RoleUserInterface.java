package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.userinterface.UserInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor
public class RoleUserInterface extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@ManyToOne @NotNull private Role role;
	
	@ManyToOne @NotNull private UserInterface userInterface;
	
	public RoleUserInterface(Role role,UserInterface userInterface) {
		super();
		this.role = role;
		this.userInterface = userInterface;
	}
	
	@Override
	public String toString() {
		return userInterface.getName();
	}
	
	/**/
	
	public static final String FIELD_ROLE = "role";
	public static final String FIELD_USER_INTERFACE = "userInterface";
}
