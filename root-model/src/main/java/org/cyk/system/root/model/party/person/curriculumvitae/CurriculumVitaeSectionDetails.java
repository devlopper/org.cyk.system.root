package org.cyk.system.root.model.party.person.curriculumvitae;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.FlexiblePeriod;

@Getter @Setter @Entity 
public class CurriculumVitaeSectionDetails extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@ManyToOne
	private CurriculumVitaeSection section;
	
	@Embedded
	private FlexiblePeriod period = new FlexiblePeriod();
	
	private String details;
	
}
