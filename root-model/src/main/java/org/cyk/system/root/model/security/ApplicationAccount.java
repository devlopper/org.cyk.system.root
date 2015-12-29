package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.INTERNAL)
public class ApplicationAccount extends UserAccount implements Serializable {

	private static final long serialVersionUID = -23914558440705885L;
	
}
