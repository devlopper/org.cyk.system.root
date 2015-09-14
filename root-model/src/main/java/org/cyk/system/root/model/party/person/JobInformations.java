package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.cyk.system.root.model.geography.ContactCollection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity 
public class JobInformations extends AbstractPersonExtendedInformations implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	private String company;
	
	@OneToOne(cascade=CascadeType.ALL) private JobFunction function;
	
	@OneToOne(cascade=CascadeType.ALL) private JobTitle title;
	
	@OneToOne private ContactCollection contactCollection;
	
}
