package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.person.MedicalInformationsAllergy;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @NoArgsConstructor
public class MedicalInformationsAllergyDetails extends AbstractOutputDetails<MedicalInformationsAllergy> implements Serializable {
	
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private String allergy,reactionType,reactionResponse;
	
	public MedicalInformationsAllergyDetails(MedicalInformationsAllergy medicalInformationsAllergy) {
		super(medicalInformationsAllergy);
	}
	
	@Override
	public void setMaster(MedicalInformationsAllergy medicalInformationsAllergy) {
		super.setMaster(medicalInformationsAllergy);
		allergy = formatUsingBusiness(medicalInformationsAllergy.getAllergy());
		reactionType = medicalInformationsAllergy.getReactionType();
		reactionResponse = medicalInformationsAllergy.getReactionResponse();
	}
	
	public static final String FIELD_ALLERGY = "allergy";
	public static final String FIELD_REACTION_TYPE = "reactionType";
	public static final String FIELD_REACTION_RESPONSE = "reactionResponse";

}