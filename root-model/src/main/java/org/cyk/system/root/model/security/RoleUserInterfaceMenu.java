package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Define client access , through the user interface menu , to the system. It is MENU CENTRIC.
 * @author Christian
 *
 */
@Entity @Getter @Setter @NoArgsConstructor @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {RoleUserInterfaceMenu.FIELD_ROLE
		,RoleUserInterfaceMenu.FIELD_USER_INTERFACE_MENU})})
public class RoleUserInterfaceMenu extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@ManyToOne @NotNull private Role role;
	
	@ManyToOne @NotNull private UserInterfaceMenu userInterfaceMenu;
	
	/**/

	public static final String FIELD_ROLE = "role";
	public static final String FIELD_USER_INTERFACE_MENU = "userInterfaceMenu";
}
