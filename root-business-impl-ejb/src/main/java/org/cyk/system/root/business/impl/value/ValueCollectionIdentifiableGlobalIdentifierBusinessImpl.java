package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.value.ValueCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.value.ValueCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.value.ValueCollectionIdentifiableGlobalIdentifierDao;

public class ValueCollectionIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<ValueCollectionIdentifiableGlobalIdentifier, ValueCollectionIdentifiableGlobalIdentifierDao,ValueCollectionIdentifiableGlobalIdentifier.SearchCriteria> implements ValueCollectionIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public ValueCollectionIdentifiableGlobalIdentifierBusinessImpl(ValueCollectionIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
}
