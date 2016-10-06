package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractMedicalInformationsJoin extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @NotNull protected MedicalInformations informations;
	
	public static final String FIELD_INFORMATIONS = "informations";
}
