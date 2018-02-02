package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractActorDaoImpl<ACTOR extends AbstractActor,SEARCH_CRITERIA extends AbstractActor.AbstractSearchCriteria<ACTOR>> extends AbstractTypedDao<ACTOR> implements AbstractActorDao<ACTOR,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private static final String READ_BY_CRITERIA_FORMAT = "SELECT actor FROM %s actor WHERE "
    		+ "    ( LOCATE(LOWER(:name),LOWER(actor.person.globalIdentifier.name))                  > 0 )"
    		//+ " OR ( LOCATE(LOWER(:name),LOWER(actor.person.lastnames))              > 0 )"
    		+ " OR ( LOCATE(LOWER(:name),LOWER(actor.globalIdentifier.code))            > 0 )"
    		;
	
	private static final String READ_BY_CRITERIA_ORDERED_FORMAT = READ_BY_CRITERIA_FORMAT+" "+ORDER_BY_FORMAT;
	/*
	static String r = 
			"(EXISTS(SELECT email FROM ElectronicMailAddress email WHERE ("+QueryStringBuilder.getLikeString("email."+ElectronicMailAddress.FIELD_ADDRESS)+")"
					+ " AND email.collection = record.person.contactCollection"
					+ "))";
	*/
	private String readByPerson,readByCriteriaNameAscendingOrder,readByCriteriaNameDescendingOrder;;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByPerson, _select().where(AbstractActor.FIELD_PERSON));
		
		//registerNamedQuery(readByCriteria,String.format(READ_BY_CRITERIA_FORMAT,entityName()));
        
        //registerNamedQuery(readByCriteriaNameAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT,entityName(), "actor.person.globalIdentifier.name ASC") );
        //registerNamedQuery(readByCriteriaNameDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT,entityName(), "actor.person.globalIdentifier.name DESC") );
	}
	
	@Override
	public ACTOR readByPerson(Person person) {
		return namedQuery(readByPerson).parameter(AbstractActor.FIELD_PERSON, person).ignoreThrowable(NoResultException.class).resultOne();
	}
	/*
	@Override
	protected String getReadByCriteriaQuery(String query) {
		return or(super.getReadByCriteriaQuery(query),QueryStringBuilder.getLikeString("record.person.lastnames"),r);
	}
	
	@Override
	protected String getReadByCriteriaQueryCodeExcludedWherePart(String where) {
		return or(super.getReadByCriteriaQueryCodeExcludedWherePart(where),QueryStringBuilder.getLikeString("record.person.lastnames"),r);
	}*/
	/*
	@SuppressWarnings("unchecked")
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameterLike(Person.FIELD_LASTNAMES, ((SEARCH_CRITERIA)searchCriteria).getPerson().getLastnames());
		queryWrapper.parameterLike(ElectronicMailAddress.FIELD_ADDRESS, ((SEARCH_CRITERIA)searchCriteria).getPerson().getContactCollection().getElectronicMailAddress().getAddress());
	}
	
	@Override
	protected void processReadByFilterQueryBuilderWhereConditions(JavaPersistenceQueryLanguage jpql) {
		super.processReadByFilterQueryBuilderWhereConditions(jpql);
		jpql.setFieldName(AbstractActor.FIELD_PARTY);
		jpql.getWhere().or().lk(Person.FIELD_LASTNAMES).or().exists(ElectronicMailAddressDaoImpl.createFilter(jpql, Party.FIELD_CONTACT_COLLECTION));
		jpql.setFieldName(null);
	}
	
	@Override
	protected void listenBeforeFilter(QueryWrapper<?> queryWrapper, Filter<ACTOR> filter,DataReadConfiguration dataReadConfiguration) {
		super.listenBeforeFilter(queryWrapper, filter, dataReadConfiguration);
		queryWrapper.parameterLike(Person.FIELD_LASTNAMES,((AbstractActor.Filter<ACTOR>)filter).getPerson().getLastnames());
		queryWrapper.parameterLike(ElectronicMailAddress.FIELD_ADDRESS,((AbstractActor.Filter<ACTOR>)filter).getPerson().getContactCollection().getElectronicMailAddress().getAddress());
	}
	*/
	/**/
	
	/**/

}
 