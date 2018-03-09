package org.cyk.system.root.persistence.impl.time;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodCollectionDao;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.MethodHelper;

public class IdentifiablePeriodCollectionDaoImpl extends AbstractCollectionDaoImpl<IdentifiablePeriodCollection,IdentifiablePeriod> implements IdentifiablePeriodCollectionDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;

	@Override
	public Collection<IdentifiablePeriodCollection> readByTypeByJoin(IdentifiablePeriodCollectionType type, AbstractIdentifiable join) {
		Collection<IdentifiablePeriodCollectionIdentifiableGlobalIdentifier> movementCollectionIdentifiableGlobalIdentifiers 
			= inject(IdentifiablePeriodCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(join);
		return MethodHelper.getInstance().callGet(CollectionHelper.getInstance().filter(movementCollectionIdentifiableGlobalIdentifiers, FieldHelper.getInstance().buildPath(
				IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_PERIOD_COLLECTION,IdentifiablePeriodCollection.FIELD_TYPE), type),IdentifiablePeriodCollection.class
				,IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_PERIOD_COLLECTION);
	}
	
}
 