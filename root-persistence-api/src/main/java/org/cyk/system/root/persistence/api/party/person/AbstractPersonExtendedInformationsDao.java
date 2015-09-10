package org.cyk.system.root.persistence.api.party.person;

import org.cyk.system.root.model.party.person.AbstractPersonExtendedInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.AbstractPartyExtendedInformationsDao;

public interface AbstractPersonExtendedInformationsDao<INFORMATIONS extends AbstractPersonExtendedInformations> extends AbstractPartyExtendedInformationsDao<INFORMATIONS,Person> {


}
