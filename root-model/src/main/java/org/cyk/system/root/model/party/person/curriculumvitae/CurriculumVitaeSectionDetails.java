package org.cyk.system.root.model.party.person.curriculumvitae;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.time.InstantInterval;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //@Entity 
public class CurriculumVitaeSectionDetails extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@ManyToOne
	private CurriculumVitaeSection section;
	
	@Embedded
	private InstantInterval instantInterval;
	
	public InstantInterval getInstantInterval(){
		if(instantInterval==null)
			instantInterval = new InstantInterval();
		return instantInterval;
	}
	
}
