package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactCollection;

@Getter @Setter @Entity 
public class JobInformations extends AbstractPersonExtendedInformations implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	private String company;
	
	@ManyToOne private JobFunction function;
	
	@ManyToOne private JobTitle title;
	
	@OneToOne private ContactCollection contactCollection;
	
}
