package org.cyk.system.root.persistence.api.party.person;

import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;

public interface PersonDao extends AbstractPartyDao<Person,PersonSearchCriteria> {

	Collection<Event> readBirthDateDayOfYearBetween(Integer fromDayOfYear,Integer toDayOfYear);
    
}
