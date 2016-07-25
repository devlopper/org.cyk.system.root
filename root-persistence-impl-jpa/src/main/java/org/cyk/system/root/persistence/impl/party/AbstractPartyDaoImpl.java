package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.Party.PartySearchCriteria;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public abstract class AbstractPartyDaoImpl<PARTY extends Party,SEARCH_CRITERIA extends PartySearchCriteria> extends AbstractTypedDao<PARTY> implements AbstractPartyDao<PARTY,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	protected String readByEmail,readByCode,readByCriteria,countByCriteria,readByCriteriaNameAscendingOrder,readByCriteriaNameDescendingOrder;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByCode, _select().where(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),
				GlobalIdentifier.FIELD_CODE));
		registerNamedQuery(readByEmail, "SELECT party FROM "+clazz.getSimpleName()+" party WHERE EXISTS("
				+ " SELECT email FROM ElectronicMail email WHERE email.address = :"+ElectronicMail.FIELD_ADDRESS+" AND email.collection = party.contactCollection"
				+ ")");
	}
	
	@Override
	public PARTY readByCode(String code) {
		return namedQuery(readByCode).parameter(GlobalIdentifier.FIELD_CODE, code).ignoreThrowable(NoResultException.class).resultOne();
	}
	
	@Override
	public PARTY readByEmail(String email) {
		return namedQuery(readByEmail).parameter(ElectronicMail.FIELD_ADDRESS, email).ignoreThrowable(NoResultException.class).resultOne();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<PARTY> readByCriteria(SEARCH_CRITERIA searchCriteria) {
		String queryName = null;
		if(searchCriteria.getName().getAscendingOrdered()!=null){
			queryName = Boolean.TRUE.equals(searchCriteria.getName().getAscendingOrdered())?
					readByCriteriaNameAscendingOrder:readByCriteriaNameDescendingOrder;
		}else
			queryName = readByCriteriaNameAscendingOrder;
		QueryWrapper<?> queryWrapper = namedQuery(queryName);
		applyCriteriaParameters(queryWrapper, searchCriteria);
		return (Collection<PARTY>) queryWrapper.resultMany();
	}

	@Override
	public Long countByCriteria(SEARCH_CRITERIA searchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(countByCriteria);
		applyCriteriaParameters(queryWrapper, searchCriteria);
		return (Long) queryWrapper.resultOne();
	}
	
	protected void applyCriteriaParameters(QueryWrapper<?> queryWrapper,SEARCH_CRITERIA searchCriteria){
		queryWrapper.parameter(GlobalIdentifier.FIELD_NAME,searchCriteria.getName().getPreparedValue());
	}
	
	
                
    /**/
	
}
 