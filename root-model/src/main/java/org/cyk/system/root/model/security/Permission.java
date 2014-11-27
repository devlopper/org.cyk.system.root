package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.INTERNAL)
public class Permission extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	public Permission(String code) {
		super(code, code, null, null);
	}

	
}