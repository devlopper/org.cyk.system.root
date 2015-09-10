package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.AbstractPersonExtendedInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.AbstractPersonExtendedInformationsDao;
import org.cyk.system.root.persistence.impl.party.AbstractPartyExtendedInformationsDaoImpl;

public abstract class AbstractPersonExtendedInformationsDaoImpl<INFORMATIONS extends AbstractPersonExtendedInformations> extends AbstractPartyExtendedInformationsDaoImpl<INFORMATIONS, Person> implements AbstractPersonExtendedInformationsDao<INFORMATIONS>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
}
 