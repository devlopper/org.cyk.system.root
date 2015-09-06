package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class RoleSecuredView extends AbstractSecuredView<Role> implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	public RoleSecuredView(Role role,String viewId,String code, String name) {
		super(role, viewId, code, name);
	}

}