package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.cyk.system.root.model.event.RepeatedEvent;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class PersonExtendedInformations extends AbstractPersonExtendedInformations implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) private Location birthLocation;
	@OneToOne(cascade=CascadeType.ALL) private RepeatedEvent birthDateAnniversary;
	
	@OneToOne(cascade=CascadeType.ALL) private PersonCredentials credentials;
	@ManyToOne private MaritalStatus maritalStatus;
	@ManyToOne private PersonTitle title;
	
	/**
	 * This is an image which visually represent the signature
	 */
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	private File signatureSpecimen;

	public PersonExtendedInformations(Person party) {
		super(party);
	}
	
	public static final String FIELD_BIRTH_LOCATION = "birthLocation";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_SIGNATURE_SPECIMEN = "signatureSpecimen";
	
}
