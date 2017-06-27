package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS)
/**
 * A visible command on the user interface (button , link , ...)
 * @author Christian
 *
 */
public class UserInterfaceCommand extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	@ManyToOne @NotNull private UserInterfaceComponent component;
	
	@ManyToOne private UniformResourceLocator uniformResourceLocator;
	
	@ManyToOne private Script script;
	
	@Transient private Boolean automaticallyCreateComponent;
	
	public static final String FIELD_COMPONENT = "component";
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	public static final String FIELD_SCRIPT = "script";
	
}