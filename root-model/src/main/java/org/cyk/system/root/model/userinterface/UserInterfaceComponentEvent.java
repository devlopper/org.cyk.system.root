package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class UserInterfaceComponentEvent extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	private UserInterfaceComponent component; 
	private UserInterfaceComponentEventName event;
	
	/**
	 * Behavior as script on various interpreters
	 */
	
	private String javascript;
	
}