package org.cyk.system.root.model.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class MedicalInformations extends AbstractPersonExtendedInformations implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@ManyToOne private BloodGroup bloodGroup;
	
	//private String /*allergicReactionType,allergicReactionResponse,*/otherInformations;//TODO Other information should be modeled by customer : build a system to support it
	
	@Transient private Collection<Medication> medications = new ArrayList<>();
	@Transient private Collection<MedicalInformationsAllergy> medicalInformationsAllergies = new ArrayList<>();
	
	public MedicalInformations(Person party) {
		super(party);
	}
}
