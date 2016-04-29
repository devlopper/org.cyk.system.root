package org.cyk.system.root.persistence.api.party.person;

import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Person.SearchCriteria;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;

public interface PersonDao extends AbstractPartyDao<Person,SearchCriteria> {

	Collection<Event> readBirthDateDayOfYearBetween(Integer fromDayOfYear,Integer toDayOfYear);
    
	
}
