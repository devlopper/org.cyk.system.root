package org.cyk.system.root.model.party.person.curriculumvitae;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractEnumeration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor //@Entity
public class CurriculumVitaeSectionType extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public CurriculumVitaeSectionType(String code, String libelle) {
		super(code, libelle,null, null);
	}
	
	
}
