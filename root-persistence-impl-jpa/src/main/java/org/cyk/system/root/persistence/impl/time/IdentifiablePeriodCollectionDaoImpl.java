package org.cyk.system.root.persistence.impl.time;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class IdentifiablePeriodCollectionDaoImpl extends AbstractCollectionDaoImpl<IdentifiablePeriodCollection,IdentifiablePeriod> implements IdentifiablePeriodCollectionDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;

	@Override
	public Collection<IdentifiablePeriodCollection> readByTypeByJoin(IdentifiablePeriodCollectionType type, AbstractIdentifiable join) {
		/*Collection<IdentifiablePeriodCollectionIdentifiableGlobalIdentifier> movementCollectionIdentifiableGlobalIdentifiers 
			= inject(IdentifiablePeriodCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(join);
		return MethodHelper.getInstance().callGet(CollectionHelper.getInstance().filter(movementCollectionIdentifiableGlobalIdentifiers, FieldHelper.getInstance().buildPath(
				IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION,IdentifiablePeriodCollection.FIELD_TYPE), type),IdentifiablePeriodCollection.class
				,IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION);
		*/
		return null;
	}
	
}
 