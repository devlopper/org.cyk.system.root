package org.cyk.system.root.model.party.person.curriculumvitae;

import java.io.Serializable;

import javax.persistence.OneToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //@Entity 
public class CurriculumVitae extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@OneToOne
	private Person person;
	
}
