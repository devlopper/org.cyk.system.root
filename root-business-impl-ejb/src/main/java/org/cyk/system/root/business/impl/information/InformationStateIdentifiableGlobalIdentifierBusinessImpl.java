package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.InformationStateIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.information.InformationStateIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.information.InformationStateIdentifiableGlobalIdentifierDao;

public class InformationStateIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<InformationStateIdentifiableGlobalIdentifier, InformationStateIdentifiableGlobalIdentifierDao,InformationStateIdentifiableGlobalIdentifier.SearchCriteria> implements InformationStateIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public InformationStateIdentifiableGlobalIdentifierBusinessImpl(InformationStateIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
}
