package org.cyk.system.root.persistence.api.party;

import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;

public interface PersonDao extends AbstractPartyDao<Person,PersonSearchCriteria> {

	Collection<Event> readBirthDateDayOfYearBetween(Integer fromDayOfYear,Integer toDayOfYear);
    
}
