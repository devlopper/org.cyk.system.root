package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS) 
public class License extends AbstractIdentifiable implements Serializable {
	private static final long serialVersionUID = -1426919647717880937L;
	
}
