package org.cyk.system.root.model.party.person.curriculumvitae;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //@Entity 
public class CurriculumVitaeSection extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 397696243734118190L;

	@ManyToOne private CurriculumVitaeSectionType type;
	
	@ManyToOne private CurriculumVitae curriculumVitae;
	
}
