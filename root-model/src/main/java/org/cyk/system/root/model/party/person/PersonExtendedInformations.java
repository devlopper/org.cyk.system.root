package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.cyk.system.root.model.event.RepeatedEvent;
import org.cyk.system.root.model.geography.Location;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity 
public class PersonExtendedInformations extends AbstractPersonExtendedInformations implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) private Location birthLocation;
	@OneToOne(cascade=CascadeType.ALL) private RepeatedEvent birthDateAnniversary;
	
	@OneToOne(cascade=CascadeType.ALL) private PersonCredentials credentials;
	@ManyToOne private MaritalStatus maritalStatus;
	@OneToOne(cascade=CascadeType.ALL) private PersonTitle title;
	
}
