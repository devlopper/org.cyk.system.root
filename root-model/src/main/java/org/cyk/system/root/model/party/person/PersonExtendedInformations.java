package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.cyk.system.root.model.event.EventRepetition;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.language.LanguageCollection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class PersonExtendedInformations extends AbstractPersonExtendedInformations implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@OneToOne(cascade=CascadeType.ALL) private EventRepetition birthDateAnniversary;//we can do anniversary for any date. so how to handle that ??? 
	
	@ManyToOne private MaritalStatus maritalStatus;
	@ManyToOne private PersonTitle title;
	
	/**
	 * This is an image which visually represent the signature
	 */
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)//TODO orphan removal and cascade should be false because of global identifier
	private File signatureSpecimen;

	@OneToOne private LanguageCollection languageCollection;
	
	public PersonExtendedInformations(Person party) {
		super(party);
	}
	
	public static final String FIELD_MARITAL_STATUS = "maritalStatus";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_SIGNATURE_SPECIMEN = "signatureSpecimen";
	public static final String FIELD_LANGUAGE_COLLECTION = "languageCollection";
	
}
