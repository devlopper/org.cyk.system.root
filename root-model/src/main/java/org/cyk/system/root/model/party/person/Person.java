package org.cyk.system.root.model.party.person;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter  @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE) @Accessors(chain=true)
public class Person extends Party implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String lastnames;
	private String surname;
	@ManyToOne private Sex sex;
	@ManyToOne private Country nationality;
	
	/* Extended informations */
	
	@Transient private PersonExtendedInformations extendedInformations;
	@Transient private JobInformations jobInformations;
	@Transient private MedicalInformations medicalInformations;
	
	@Transient private String names;
	
	@Transient protected Collection<PersonRelationship> relationships;
	
	public Person(String firstName,String lastnames) {
		super(firstName);
		this.lastnames = lastnames;
	}
	
	@Override
	public Person setCode(String code) {
		return (Person) super.setCode(code);
	}
	
	@Override
	public Person setName(String name) {
		return (Person) super.setName(name);
	}
	
	public String getNames(){
		if(StringUtils.isBlank(names) && globalIdentifier!=null && StringUtils.isNotBlank(getName()))
			names = globalIdentifier.getName()+(StringUtils.isEmpty(lastnames)?Constant.EMPTY_STRING:(Constant.CHARACTER_SPACE+lastnames));
		return names;
	}

	@Override
	public String toString() {
		return getUiString();
	}

	@Override
	public String getUiString() {
		String names = getNames();
		return StringUtils.isBlank(names) ? getCode() : names;
	}
	
	/**/
	
	public static final String FIELD_LASTNAMES = "lastnames";
	public static final String FIELD_SURNAME = "surname";
	public static final String FIELD_SEX = "sex";
	public static final String FIELD_NATIONALITY = "nationality";
	public static final String FIELD_EXTENDED_INFORMATIONS = "extendedInformations";
	public static final String FIELD_JOB_INFORMATIONS = "jobInformations";
	public static final String FIELD_MEDICAL_INFORMATIONS = "medicalInformations";
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends PartySearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected StringSearchCriteria lastnames = new StringSearchCriteria();
		
		public SearchCriteria(){
			this(null);
		}
		
		public SearchCriteria(String name) {
			super(name);
			setStringSearchCriteria(lastnames, name);	
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			super.set(stringSearchCriteria);
			lastnames.set(stringSearchCriteria);
		}
		
		@Override
		public void set(String value) {
			super.set(value);
			lastnames.setValue(value);
		}
		
	}
}