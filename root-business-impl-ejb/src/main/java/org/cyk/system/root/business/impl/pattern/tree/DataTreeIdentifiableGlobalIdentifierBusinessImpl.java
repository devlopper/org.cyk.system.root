package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.pattern.tree.DataTreeIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.pattern.tree.DataTreeIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.pattern.tree.DataTreeIdentifiableGlobalIdentifierDao;

@Stateless
public class DataTreeIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<DataTreeIdentifiableGlobalIdentifier, DataTreeIdentifiableGlobalIdentifierDao,DataTreeIdentifiableGlobalIdentifier.SearchCriteria> implements DataTreeIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public DataTreeIdentifiableGlobalIdentifierBusinessImpl(DataTreeIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	@Override
	public DataTreeIdentifiableGlobalIdentifier create(DataTreeIdentifiableGlobalIdentifier dataTreeIdentifiableGlobalIdentifier) {
		//if(fileIdentifiableGlobalIdentifier.getFile().getIdentifier()==null)
		//	RootBusinessLayer.getInstance().getFileBusiness().create(fileIdentifiableGlobalIdentifier.getFile());
		return super.create(dataTreeIdentifiableGlobalIdentifier);
	}
	
	@Override
	public DataTreeIdentifiableGlobalIdentifier update(DataTreeIdentifiableGlobalIdentifier dataTreeIdentifiableGlobalIdentifier) {
		//RootBusinessLayer.getInstance().getFileBusiness().update(fileIdentifiableGlobalIdentifier.getFile());
		return super.update(dataTreeIdentifiableGlobalIdentifier);
	}
	
	@Override
	public DataTreeIdentifiableGlobalIdentifier delete(DataTreeIdentifiableGlobalIdentifier dataTreeIdentifiableGlobalIdentifier) {
		//RootBusinessLayer.getInstance().getFileBusiness().delete(dataTreeIdentifiableGlobalIdentifier.getFile());
		//fileIdentifiableGlobalIdentifier.setFile(null);
		//fileIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(null);
		return super.delete(dataTreeIdentifiableGlobalIdentifier);
	}
	
}
