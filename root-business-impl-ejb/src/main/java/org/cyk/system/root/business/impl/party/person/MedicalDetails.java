package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MedicalDetails extends AbstractOutputDetails<Person> implements Serializable {
	
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private String bloodGroup/*,allergicReactionResponse,allergicReactionType*/,allergies,medications,otherInformations;
	
	public MedicalDetails(Person person) {
		super(person);
		if(person.getMedicalInformations()!=null){
			if(person.getMedicalInformations().getBloodGroup()!=null)
				bloodGroup = person.getMedicalInformations().getBloodGroup().getName();
			//allergicReactionResponse = person.getMedicalInformations().getAllergicReactionResponse();
			//allergicReactionType = person.getMedicalInformations().getAllergicReactionType();
			otherInformations = person.getMedicalInformations().getOtherInformations();
			allergies = StringUtils.join(person.getMedicalInformations().getMedicalInformationsAllergies(),Constant.CHARACTER_COMA);
			medications = StringUtils.join(person.getMedicalInformations().getMedications(),Constant.CHARACTER_COMA);
		}
	}
}