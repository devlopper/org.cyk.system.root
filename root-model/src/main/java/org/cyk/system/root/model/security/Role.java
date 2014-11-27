package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.INTERNAL)
public class Role extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	public static final String ADMINISTRATOR = "ADMINISTRATOR";
	public static final String MANAGER = "MANAGER";
	public static final String REGISTERED = "REGISTERED";
	
	@ManyToMany
    @JoinTable(name="RolePermissions",joinColumns = { @JoinColumn(name = "roleid") } ,inverseJoinColumns={ @JoinColumn(name = "permissionid") })
	private Set<Permission> permissions = new HashSet<>();
	
	public Role(String code, String name) {
		super(code, name, null, null);
	}

	
}