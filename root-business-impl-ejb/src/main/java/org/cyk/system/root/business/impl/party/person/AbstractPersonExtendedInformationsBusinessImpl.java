package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import org.cyk.system.root.business.api.party.person.AbstractPersonExtendedInformationsBusiness;
import org.cyk.system.root.business.impl.party.AbstractPartyExtendedInformationsBusinessImpl;
import org.cyk.system.root.model.party.person.AbstractPersonExtendedInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.AbstractPersonExtendedInformationsDao;

public abstract class AbstractPersonExtendedInformationsBusinessImpl<INFORMATIONS extends AbstractPersonExtendedInformations,DAO extends AbstractPersonExtendedInformationsDao<INFORMATIONS>> extends AbstractPartyExtendedInformationsBusinessImpl<INFORMATIONS, DAO,Person> implements AbstractPersonExtendedInformationsBusiness<INFORMATIONS>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractPersonExtendedInformationsBusinessImpl(DAO dao) {
		super(dao); 
	}
	
}
