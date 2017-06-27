package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class UserInterfaceMenuItem extends AbstractCollectionItem<UserInterfaceMenu> implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	@ManyToOne @NotNull private UserInterfaceMenuNode node;
	
	/**/
	
	public static final String FIELD_NODE = "node";
}