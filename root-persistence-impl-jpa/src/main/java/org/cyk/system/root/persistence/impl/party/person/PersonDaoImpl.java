package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.party.AbstractPartyDaoImpl;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public class PersonDaoImpl extends AbstractPartyDaoImpl<Person> implements PersonDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	public static final String READ_BY_CRITERIA_QUERY = 
			"(EXISTS(SELECT email FROM ElectronicMailAddress email WHERE ("+QueryStringBuilder.getLikeString("email."+ElectronicMailAddress.FIELD_ADDRESS)+")"
					+ " AND email.collection = record.contactCollection"
					+ "))";
	
	@Override
	protected String getReadByCriteriaQuery(String query) {
		return or(super.getReadByCriteriaQuery(query),QueryStringBuilder.getLikeString("record.lastnames"),READ_BY_CRITERIA_QUERY);
	}
	
	@Override
	protected String getReadByCriteriaQueryCodeExcludedWherePart(String where) {
		return or(super.getReadByCriteriaQueryCodeExcludedWherePart(where),QueryStringBuilder.getLikeString("record.lastnames"),READ_BY_CRITERIA_QUERY);
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
		queryWrapper.parameterLike(ElectronicMailAddress.FIELD_ADDRESS, ((Person.SearchCriteria)searchCriteria).getContactCollection().getElectronicMailAddress().getAddress());
	}
	
	public static JavaPersistenceQueryLanguage createFilter(JavaPersistenceQueryLanguage jpql){
		return (JavaPersistenceQueryLanguage) jpql.getWhere().lk(Person.FIELD_LASTNAMES).getParent();	
	}
	
}
 