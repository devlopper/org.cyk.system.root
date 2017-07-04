package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.cyk.system.root.model.IdentifiableRuntimeCollection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class MedicalInformations extends AbstractPersonExtendedInformations implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@ManyToOne private BloodGroup bloodGroup;
	
	@Transient private IdentifiableRuntimeCollection<MedicalInformationsMedication> medicalInformationsMedications = new IdentifiableRuntimeCollection<>();
	@Transient private IdentifiableRuntimeCollection<MedicalInformationsAllergy> medicalInformationsAllergies = new IdentifiableRuntimeCollection<>();
	
	public MedicalInformations(Person party) {
		super(party);
	}
}
