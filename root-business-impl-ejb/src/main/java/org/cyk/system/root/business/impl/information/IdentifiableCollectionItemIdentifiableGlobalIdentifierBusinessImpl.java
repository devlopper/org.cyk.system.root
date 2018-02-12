package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.IdentifiableCollectionItemIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.information.IdentifiableCollectionItemIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionItemIdentifiableGlobalIdentifierDao;

public class IdentifiableCollectionItemIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<IdentifiableCollectionItemIdentifiableGlobalIdentifier, IdentifiableCollectionItemIdentifiableGlobalIdentifierDao,IdentifiableCollectionItemIdentifiableGlobalIdentifier.SearchCriteria> implements IdentifiableCollectionItemIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public IdentifiableCollectionItemIdentifiableGlobalIdentifierBusinessImpl(IdentifiableCollectionItemIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
}