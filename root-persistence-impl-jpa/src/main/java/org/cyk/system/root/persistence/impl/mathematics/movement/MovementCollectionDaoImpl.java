package org.cyk.system.root.persistence.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.MethodHelper;

public class MovementCollectionDaoImpl extends AbstractCollectionDaoImpl<MovementCollection,Movement> implements MovementCollectionDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;

	@Override
	public Collection<MovementCollection> readByTypeByJoin(MovementCollectionType type, AbstractIdentifiable join) {
		Collection<MovementCollectionIdentifiableGlobalIdentifier> movementCollectionIdentifiableGlobalIdentifiers 
			= inject(MovementCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(join);
		return MethodHelper.getInstance().callGet(CollectionHelper.getInstance().filter(movementCollectionIdentifiableGlobalIdentifiers, FieldHelper.getInstance().buildPath(
				MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION,MovementCollection.FIELD_TYPE), type),MovementCollection.class
				,MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION);
	}
	
}
 