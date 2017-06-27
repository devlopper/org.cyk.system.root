package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuLocation;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) @Deprecated
public class ApplicationUserInterfaceMenu extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	@ManyToOne @NotNull private Application application;
	@ManyToOne @NotNull private UserInterfaceMenu menu;
	@ManyToOne @NotNull private UserInterfaceMenuLocation menuLocation;
	
	public static final String FIELD_APPLICATION = "application";
	public static final String FIELD_MENU_LOCATION = "menuLocation";
	public static final String FIELD_MENU = "menu";
	
}