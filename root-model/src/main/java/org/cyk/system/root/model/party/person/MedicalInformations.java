package org.cyk.system.root.model.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity 
public class MedicalInformations extends AbstractPersonExtendedInformations implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@OneToOne(cascade=CascadeType.ALL) private BloodGroup bloodGroup;
	
	private String allergicReactionType,allergicReactionResponse,otherInformations;
	
	@Transient private Collection<Medication> medications = new ArrayList<>();
	@Transient private Collection<Allergy> allergies = new ArrayList<>();
}
