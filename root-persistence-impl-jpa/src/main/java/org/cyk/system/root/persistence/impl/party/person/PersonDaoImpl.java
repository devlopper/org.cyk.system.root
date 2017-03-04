package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Person.SearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.party.AbstractPartyDaoImpl;

public class PersonDaoImpl extends AbstractPartyDaoImpl<Person,SearchCriteria> implements PersonDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected String getReadByCriteriaQuery(String query) {
		return super.getReadByCriteriaQuery(query)+" OR "+QueryStringBuilder.getLikeString("record.lastnames", ":lastnames");
	}
	
	@Override
	protected String getReadByCriteriaQueryCodeExcludedWherePart(String where) {
		return super.getReadByCriteriaQueryCodeExcludedWherePart(where)+" OR "+QueryStringBuilder.getLikeString("record.lastnames", ":lastnames");
	}

	@Override
	public Collection<Event> readBirthDateDayOfYearBetween(Integer fromDayOfYear, Integer toDayOfYear) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameterLike(Person.FIELD_LASTNAMES, ((Person.SearchCriteria)searchCriteria).getLastnames());
	}

}
 