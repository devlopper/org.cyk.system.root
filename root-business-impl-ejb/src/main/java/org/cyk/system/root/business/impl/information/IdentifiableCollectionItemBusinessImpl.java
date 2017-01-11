package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.IdentifiableCollectionItemBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.information.IdentifiableCollectionItem;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionItemDao;

public class IdentifiableCollectionItemBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<IdentifiableCollectionItem, IdentifiableCollectionItemDao,IdentifiableCollectionItem.SearchCriteria> implements IdentifiableCollectionItemBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public IdentifiableCollectionItemBusinessImpl(IdentifiableCollectionItemDao dao) {
		super(dao); 
	}
	/*
	@Override
	protected IdentifiableCollectionItem __instanciateOne__(String[] values, InstanciateOneListener<IdentifiableCollectionItem> listener) {
		super.__instanciateOne__(values, listener);
		set(listener.getSetListener(), IdentifiableCollectionItem.FIELD_COLLECTION);
		return listener.getInstance();
	}
	*/
}
