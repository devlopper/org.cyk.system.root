package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Entity @Getter @Setter @NoArgsConstructor
public class RoleBusinessService extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
	@ManyToOne @NotNull private Role role;
	
	@ManyToOne @NotNull private BusinessService businessService;
	
	public RoleBusinessService(Role role,BusinessService businessService) {
		super();
		this.role = role;
		this.businessService = businessService;
	}
	
	@Override
	public String toString() {
		return businessService.getName();
	}
	
	/**/
	
	public static final String FIELD_ROLE = "role";
	public static final String FIELD_BUSINESS_SERVIVE = "businessService";
}
