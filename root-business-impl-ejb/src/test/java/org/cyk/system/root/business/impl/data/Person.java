package org.cyk.system.root.business.impl.data;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter  
//@Entity 
@NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class Person extends Party implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String lastnames;
	private String surname;
	@ManyToOne private Sex sex;
	@ManyToOne private Country nationality;
	/*
	@Transient private PersonExtendedInformations extendedInformations;
	@Transient private JobInformations jobInformations;
	@Transient private MedicalInformations medicalInformations;
	*/
}