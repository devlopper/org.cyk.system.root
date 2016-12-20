package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public abstract class AbstractActorDaoImpl<ACTOR extends AbstractActor,SEARCH_CRITERIA extends AbstractActor.AbstractSearchCriteria<ACTOR>> extends AbstractTypedDao<ACTOR> implements AbstractActorDao<ACTOR,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private static final String READ_BY_CRITERIA_FORMAT = "SELECT actor FROM %s actor WHERE "
    		+ "    ( LOCATE(LOWER(:name),LOWER(actor.person.globalIdentifier.name))                  > 0 )"
    		+ " OR ( LOCATE(LOWER(:name),LOWER(actor.person.lastnames))              > 0 )"
    		+ " OR ( LOCATE(LOWER(:name),LOWER(actor.globalIdentifier.code))            > 0 )"
    		;
	
	private static final String READ_BY_CRITERIA_ORDERED_FORMAT = READ_BY_CRITERIA_FORMAT+" "+ORDER_BY_FORMAT;
	
	private String readByPerson,readByCriteriaNameAscendingOrder,readByCriteriaNameDescendingOrder;;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByPerson, _select().where(AbstractActor.FIELD_PERSON));
		
		registerNamedQuery(readByCriteria,String.format(READ_BY_CRITERIA_FORMAT,entityName()));
        
        registerNamedQuery(readByCriteriaNameAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT,entityName(), "actor.person.globalIdentifier.name ASC") );
        registerNamedQuery(readByCriteriaNameDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT,entityName(), "actor.person.globalIdentifier.name DESC") );
	}
	
	@Override
	public ACTOR readByPerson(Person person) {
		return namedQuery(readByPerson).parameter(AbstractActor.FIELD_PERSON, person).ignoreThrowable(NoResultException.class).resultOne();
	}
	
	@SuppressWarnings("unchecked")
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
	}
	
	/**/
	
	/**/

}
 