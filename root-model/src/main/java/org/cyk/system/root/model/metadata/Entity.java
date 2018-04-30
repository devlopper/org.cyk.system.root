package org.cyk.system.root.model.metadata;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @javax.persistence.Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class Entity extends AbstractEnumeration implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	private Integer minimumNumberOfInstance,maximumNumberOfInstance;
	
}

