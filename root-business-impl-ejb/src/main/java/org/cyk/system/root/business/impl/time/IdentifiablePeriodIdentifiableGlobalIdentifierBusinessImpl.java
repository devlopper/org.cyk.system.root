package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.IdentifiablePeriodIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.time.IdentifiablePeriodIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodIdentifiableGlobalIdentifierDao;

public class IdentifiablePeriodIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<IdentifiablePeriodIdentifiableGlobalIdentifier, IdentifiablePeriodIdentifiableGlobalIdentifierDao,IdentifiablePeriodIdentifiableGlobalIdentifier.SearchCriteria> implements IdentifiablePeriodIdentifiableGlobalIdentifierBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public IdentifiablePeriodIdentifiableGlobalIdentifierBusinessImpl(IdentifiablePeriodIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	
}
