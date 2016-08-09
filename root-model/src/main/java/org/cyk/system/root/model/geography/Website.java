package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class Website extends Contact implements Serializable {

	private static final long serialVersionUID = 923076998880521464L;

	@OneToOne @NotNull private UniformResourceLocator uniformResourceLocator;
	
	public Website(UniformResourceLocator uniformResourceLocator) {
		this.uniformResourceLocator = uniformResourceLocator;
	}
	
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	
}
