package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter  @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class Person extends Party implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String lastName;//TODO to be changed to lastnames
	private String surname;
	@ManyToOne private Sex sex;
	@ManyToOne private Country nationality;
	
	/* Extended informations */
	
	@Transient private PersonExtendedInformations extendedInformations;
	@Transient private JobInformations jobInformations;
	@Transient private MedicalInformations medicalInformations;
	
	public Person(String firstName,String lastName) {
		super(firstName);
		this.lastName = lastName;
	}
	
	public String getNames(){
		return name+(StringUtils.isEmpty(lastName)?"":(" "+lastName));
	}

	@Override
	public String toString() {
		return getNames();
	}

	@Override
	public String getUiString() {
		return getNames();
	}
	
	/**/
	
	public static final String FIELD_LASTNAME = "lastName";
	public static final String FIELD_SURNAME = "surname";
	public static final String FIELD_SEX = "sex";
	public static final String FIELD_NATIONALITY = "nationality";
	public static final String FIELD_EXTENDED_INFORMATIONS = "extendedInformations";
	public static final String FIELD_JOB_INFORMATIONS = "jobInformations";
}