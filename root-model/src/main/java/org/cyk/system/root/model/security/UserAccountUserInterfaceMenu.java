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
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {UserAccountUserInterfaceMenu.FIELD_USER_ACCOUNT
		,UserAccountUserInterfaceMenu.FIELD_USER_INTERFACE_MENU})})
public class UserAccountUserInterfaceMenu extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@ManyToOne @NotNull private UserAccount userAccount;
	
	@ManyToOne @NotNull private UserInterfaceMenu userInterfaceMenu;
	
	/**/

	public static final String FIELD_USER_ACCOUNT = "userAccount";
	public static final String FIELD_USER_INTERFACE_MENU = "userInterfaceMenu";
}
