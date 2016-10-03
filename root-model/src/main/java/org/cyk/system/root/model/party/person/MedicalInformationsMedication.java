package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor @Entity
public class MedicalInformationsMedication extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @NotNull private MedicalInformations informations;
	
	@ManyToOne @NotNull private Medication medication;
	
	private Boolean mustBeAvailable;
	
	private Boolean mustBeGiven;
	
}
