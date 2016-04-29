package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Person.SearchCriteria;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.impl.party.AbstractPartyDaoImpl;

public class PersonDaoImpl extends AbstractPartyDaoImpl<Person,SearchCriteria> implements PersonDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private static final String READ_BY_CRITERIA_FORMAT = "SELECT person FROM Person person WHERE "
    		+ "    ( LOCATE(LOWER(:name),LOWER(person.name))                > 0 )"
    		+ " OR ( LOCATE(LOWER(:name),LOWER(person.lastName))            > 0 )"
    		;
	
	private static final String READ_BY_CRITERIA_ORDERED_FORMAT = READ_BY_CRITERIA_FORMAT+" "+ORDER_BY_FORMAT;

	@Override
    protected void namedQueriesInitialisation() {
        
    	super.namedQueriesInitialisation();
    	registerNamedQuery(readByCriteria,READ_BY_CRITERIA_FORMAT);
        
        registerNamedQuery(readByCriteriaNameAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "person.name ASC") );
        registerNamedQuery(readByCriteriaNameDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "person.name DESC") );
        
    }

	@Override
	public Collection<Event> readBirthDateDayOfYearBetween(Integer fromDayOfYear, Integer toDayOfYear) {
		// TODO Auto-generated method stub
		return null;
	}

}
 