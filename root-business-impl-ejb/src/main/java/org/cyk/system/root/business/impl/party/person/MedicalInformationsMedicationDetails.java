package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.person.MedicalInformationsMedication;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @NoArgsConstructor
public class MedicalInformationsMedicationDetails extends AbstractOutputDetails<MedicalInformationsMedication> implements Serializable {
	
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private String medication,mustBeAvailable,mustBeGiven;
	
	public MedicalInformationsMedicationDetails(MedicalInformationsMedication medicalInformationsMedication) {
		super(medicalInformationsMedication);
	}
	
	@Override
	public void setMaster(MedicalInformationsMedication medicalInformationsMedication) {
		super.setMaster(medicalInformationsMedication);
		medication = formatUsingBusiness(medicalInformationsMedication.getMedication());
		mustBeAvailable = formatResponse(medicalInformationsMedication.getMustBeAvailable());
		mustBeGiven = formatResponse(medicalInformationsMedication.getMustBeGiven());
	}
	
	public static final String FIELD_MEDICATION = "medication";
	public static final String FIELD_MUST_BE_AVAILABLE = "mustBeAvailable";
	public static final String FIELD_MUST_BE_GIVEN = "mustBeGiven";

}