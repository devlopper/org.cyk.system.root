package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.ENUMERATION)
public class UserInterfaceMenuLocation extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	public UserInterfaceMenuLocation(String code,String name) {
		super(code, name,null,null);
	}

	
}