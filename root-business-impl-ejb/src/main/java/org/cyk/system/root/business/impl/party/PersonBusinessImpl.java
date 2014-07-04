package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.party.PersonBusiness;
import org.cyk.system.root.model.party.Person;
import org.cyk.system.root.persistence.api.party.PersonDao;

@Stateless
public class PersonBusinessImpl extends AbstractPartyBusinessImpl<Person, PersonDao> implements PersonBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PersonBusinessImpl(PersonDao dao) {
		super(dao); 
	}  

    
}
 