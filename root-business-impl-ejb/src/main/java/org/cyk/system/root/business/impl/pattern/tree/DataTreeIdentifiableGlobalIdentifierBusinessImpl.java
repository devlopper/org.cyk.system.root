package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.pattern.tree.DataTreeIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.pattern.tree.DataTreeIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.pattern.tree.DataTreeIdentifiableGlobalIdentifierDao;

public class DataTreeIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<DataTreeIdentifiableGlobalIdentifier, DataTreeIdentifiableGlobalIdentifierDao,DataTreeIdentifiableGlobalIdentifier.SearchCriteria> implements DataTreeIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public DataTreeIdentifiableGlobalIdentifierBusinessImpl(DataTreeIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
}
