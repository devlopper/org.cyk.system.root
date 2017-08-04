package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @NoArgsConstructor
public class MedicalDetails extends AbstractOutputDetails<Person> implements Serializable {
	
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private String bloodGroup,otherMedicalInformations;
	
	public MedicalDetails(Person person) {
		super(person);
	}
	
	@Override
	public void setMaster(Person person) {
		super.setMaster(person);
		if(person.getMedicalInformations()!=null){
			if(person.getMedicalInformations().getBloodGroup()!=null)
				bloodGroup = person.getMedicalInformations().getBloodGroup().getName();
			otherMedicalInformations = person.getMedicalInformations().getOtherDetails();
		}
	}
	
	public static final String LABEL_IDENTIFIER = "medical";
	
	public static final String FIELD_BLOOD_GROUP = "bloodGroup";
	public static final String FIELD_OTHER_MEDICAL_INFORMATIONS = "otherMedicalInformations";

}