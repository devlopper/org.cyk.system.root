package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) 
public class Role extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	@ManyToMany
    @JoinTable(name="RolePermissions",joinColumns = { @JoinColumn(name = "roleid") } ,inverseJoinColumns={ @JoinColumn(name = "permissionid") })
	private Set<Permission> permissions = new HashSet<>();
	
	@Transient private Set<RoleUniformResourceLocator> roleUniformResourceLocators = new HashSet<>();
	@Transient private Set<RoleBusinessService> roleBusinessServices;
	@Transient private Set<RoleUserInterface> roleUserInterfaces;
	
	public Role(String code, String name) {
		super(code, name, null, null);
	}

	@Override
	public boolean equals(Object object) {
		return (object instanceof Role) && ((Role)object).getCode().equals(getCode());
	}
	
	@Override
	public int hashCode() {
		return StringUtils.isBlank(getCode())?31:getCode().hashCode();
	}
	
	/**/
	
	public static final String ADMINISTRATOR = "ADMINISTRATOR";
	public static final String MANAGER = "MANAGER";
	public static final String SECURITY_MANAGER = "SECURITY_MANAGER";
	public static final String SETTING_MANAGER = "SETTING_MANAGER";
	public static final String USER = "USER";
}