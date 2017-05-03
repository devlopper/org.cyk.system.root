package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public abstract class AbstractActorDaoImpl<ACTOR extends AbstractActor,SEARCH_CRITERIA extends AbstractActor.AbstractSearchCriteria<ACTOR>> extends AbstractTypedDao<ACTOR> implements AbstractActorDao<ACTOR,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private static final String READ_BY_CRITERIA_FORMAT = "SELECT actor FROM %s actor WHERE "
    		+ "    ( LOCATE(LOWER(:name),LOWER(actor.person.globalIdentifier.name))                  > 0 )"
    		+ " OR ( LOCATE(LOWER(:name),LOWER(actor.person.lastnames))              > 0 )"
    		+ " OR ( LOCATE(LOWER(:name),LOWER(actor.globalIdentifier.code))            > 0 )"
    		;
	
	private static final String READ_BY_CRITERIA_ORDERED_FORMAT = READ_BY_CRITERIA_FORMAT+" "+ORDER_BY_FORMAT;
	
	static String r = 
			"(EXISTS(SELECT email FROM ElectronicMail email WHERE ("+QueryStringBuilder.getLikeString("email."+ElectronicMail.FIELD_ADDRESS)+")"
					+ " AND email.collection = record.person.contactCollection"
					+ "))";
	
	private String readByPerson,readByCriteriaNameAscendingOrder,readByCriteriaNameDescendingOrder;;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByPerson, _select().where(AbstractActor.FIELD_PERSON));
		
		//registerNamedQuery(readByCriteria,String.format(READ_BY_CRITERIA_FORMAT,entityName()));
        
        registerNamedQuery(readByCriteriaNameAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT,entityName(), "actor.person.globalIdentifier.name ASC") );
        registerNamedQuery(readByCriteriaNameDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT,entityName(), "actor.person.globalIdentifier.name DESC") );
	}
	
	@Override
	public ACTOR readByPerson(Person person) {
		return namedQuery(readByPerson).parameter(AbstractActor.FIELD_PERSON, person).ignoreThrowable(NoResultException.class).resultOne();
	}
	
	@Override
	protected String getReadByCriteriaQuery(String query) {
		return or(super.getReadByCriteriaQuery(query),QueryStringBuilder.getLikeString("record.person.lastnames"),r);
	}
	
	@Override
	protected String getReadByCriteriaQueryCodeExcludedWherePart(String where) {
		return or(super.getReadByCriteriaQueryCodeExcludedWherePart(where),QueryStringBuilder.getLikeString("record.person.lastnames"),r);
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public Collection<ACTOR> readByCriteria(SEARCH_CRITERIA searchCriteria) {
		String queryName = null;
		if(searchCriteria.getPerson().getName().getAscendingOrdered()!=null){
			queryName = Boolean.TRUE.equals(searchCriteria.getPerson().getName().getAscendingOrdered())?
					readByCriteriaNameAscendingOrder:readByCriteriaNameDescendingOrder;
		}else
			queryName = readByCriteriaNameAscendingOrder;
		QueryWrapper<?> queryWrapper = namedQuery(queryName);
		applyCriteriaParameters(queryWrapper, searchCriteria);
		return (Collection<ACTOR>) queryWrapper.resultMany();
	}

	@Override
	public Long countByCriteria(SEARCH_CRITERIA searchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(countByCriteria);
		applyCriteriaParameters(queryWrapper, searchCriteria);
		return (Long) queryWrapper.resultOne();
	}
	
	protected void applyCriteriaParameters(QueryWrapper<?> queryWrapper,SEARCH_CRITERIA searchCriteria){
		queryWrapper.parameter(GlobalIdentifier.FIELD_NAME,searchCriteria.getPerson().getName().getPreparedValue());
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameterLike(Person.FIELD_LASTNAMES, ((SEARCH_CRITERIA)searchCriteria).getPerson().getLastnames());
		queryWrapper.parameterLike(ElectronicMail.FIELD_ADDRESS, ((SEARCH_CRITERIA)searchCriteria).getPerson().getContactCollection().getElectronicMail().getAddress());
	}
	
	/**/
	
	/**/

}
 