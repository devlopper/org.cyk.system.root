package org.cyk.system.root.persistence.impl.time;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;

public class IdentifiablePeriodDaoImpl extends AbstractCollectionItemDaoImpl<IdentifiablePeriod,IdentifiablePeriodCollection> implements IdentifiablePeriodDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;
	
	protected String readByCollectionByClosed,countByCollectionByClosed;

	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByCollectionByClosed, _select().where(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CLOSED)
				,GlobalIdentifier.FIELD_CLOSED).and(IdentifiablePeriod.FIELD_COLLECTION));
	}
	
	@Override
	public Collection<IdentifiablePeriod> readByCollectionByClosed(IdentifiablePeriodCollection collection,Boolean closed) {
		return namedQuery(readByCollectionByClosed).parameter(IdentifiablePeriod.FIELD_COLLECTION, collection)
				.parameter(GlobalIdentifier.FIELD_CLOSED, closed).resultMany();
	}
	
	@Override
	public Long countByCollectionByClosed(IdentifiablePeriodCollection collection,Boolean closed) {
		return countNamedQuery(countByCollectionByClosed).parameter(IdentifiablePeriod.FIELD_COLLECTION, collection)
				.parameter(GlobalIdentifier.FIELD_CLOSED, closed).resultOne();
	}
}
 