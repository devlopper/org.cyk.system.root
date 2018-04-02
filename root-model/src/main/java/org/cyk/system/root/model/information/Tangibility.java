package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.ENUMERATION,genderType=GenderType.MALE)
public class Tangibility extends AbstractEnumeration implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

}

