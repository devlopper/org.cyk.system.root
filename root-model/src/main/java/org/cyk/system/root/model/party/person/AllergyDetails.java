package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity
public class AllergyDetails extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@ManyToOne private MedicalInformations medicalInformations;
	
	@ManyToOne private Allergy allergy;
	
	private String reactionType,reactionResponse;
		

}
