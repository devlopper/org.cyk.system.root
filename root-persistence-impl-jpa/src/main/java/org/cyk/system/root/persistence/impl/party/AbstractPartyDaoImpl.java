package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartySearchCriteria;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public abstract class AbstractPartyDaoImpl<PARTY extends Party,SEARCH_CRITERIA extends PartySearchCriteria> extends AbstractTypedDao<PARTY> implements AbstractPartyDao<PARTY,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	protected String readByCriteria,countByCriteria,readByCriteriaNameAscendingOrder,readByCriteriaNameDescendingOrder;
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<PARTY> readByCriteria(SEARCH_CRITERIA searchCriteria) {
		String queryName = null;
		if(searchCriteria.getNameSearchCriteria().getAscendingOrdered()!=null){
			queryName = Boolean.TRUE.equals(searchCriteria.getNameSearchCriteria().getAscendingOrdered())?
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
		queryWrapper.parameter("name",searchCriteria.getNameSearchCriteria().getPreparedValue());
	}
                
    
	
}
 