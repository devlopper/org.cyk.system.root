package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
@ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
/**
 * A visible thing on the user interface (button , link , panel , table and so on)
 * @author Christian
 *
 */
public class UserInterfaceComponent extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	@Transient private IdentifiableRuntimeCollection<UserInterfaceComponentEvent> userInterfaceComponentEventCollection;

	public IdentifiableRuntimeCollection<UserInterfaceComponentEvent> getUserInterfaceComponentEventCollection(){
		if(userInterfaceComponentEventCollection==null)
			userInterfaceComponentEventCollection = new IdentifiableRuntimeCollection<>();
		return userInterfaceComponentEventCollection;
	}
	
}