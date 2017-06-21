package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) 
public class Role extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	@ManyToMany
    @JoinTable(name="RolePermissions",joinColumns = { @JoinColumn(name = "roleid") } ,inverseJoinColumns={ @JoinColumn(name = "permissionid") })
	private Set<Permission> permissions = new HashSet<>();
	
	@Transient private IdentifiableRuntimeCollection<RoleUniformResourceLocator> roleUniformResourceLocators = new IdentifiableRuntimeCollection<>();
	
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
	
}