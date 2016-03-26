package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.userinterface.MenuItem;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS) 
public class RoleMenuItem extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@ManyToOne @NotNull private Role role;
	
	@ManyToOne @NotNull private MenuItem menuItem;
	
	public RoleMenuItem(Role role,MenuItem menuItem) {
		super();
		this.role = role;
		this.menuItem = menuItem;
	}
	
	@Override
	public String toString() {
		return menuItem.toString();
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
				+ ((menuItem == null) ? 0
						: menuItem.hashCode());
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
		RoleMenuItem other = (RoleMenuItem) obj;
		if(identifier!=null && other.identifier!=null)
			return identifier.equals(other.identifier);
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (menuItem == null) {
			if (other.menuItem != null)
				return false;
		} else if (!menuItem.equals(other.menuItem))
			return false;
		return true;
	}

	/**/

	public static final String FIELD_ROLE = "role";
	public static final String FIELD_MENU_ITEM = "menuItem";
}
