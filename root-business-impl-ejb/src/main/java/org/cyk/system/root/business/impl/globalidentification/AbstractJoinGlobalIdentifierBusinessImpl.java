package org.cyk.system.root.business.impl.globalidentification;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public abstract class AbstractJoinGlobalIdentifierBusinessImpl<IDENTIFIABLE extends AbstractIdentifiable,DAO extends JoinGlobalIdentifierDao<IDENTIFIABLE, SEARCH_CRITERIA>,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> extends AbstractTypedBusinessService<IDENTIFIABLE, DAO> implements JoinGlobalIdentifierBusiness<IDENTIFIABLE,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	public AbstractJoinGlobalIdentifierBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByCriteria(final SEARCH_CRITERIA searchCriteria) {
		return dao.readByCriteria(searchCriteria);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(SEARCH_CRITERIA searchCriteria) {
		return dao.countByCriteria(searchCriteria);
	}
}
