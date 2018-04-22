package org.cyk.system.root.persistence.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public class MovementCollectionDaoImpl extends AbstractCollectionDaoImpl<MovementCollection,Movement> implements MovementCollectionDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;

	@Override
	protected void listenInstanciateJpqlBuilder(String name, JavaPersistenceQueryLanguage builder) {
		super.listenInstanciateJpqlBuilder(name, builder);
		if(readByFilter.equals(name)){
			builder.setFieldName(MovementCollection.FIELD_BUFFER).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
		}
	}
			
	@SuppressWarnings("unchecked")
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass,QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName,arguments);
		if(ArrayUtils.contains(new String[]{readByFilter,countByFilter}, queryName)){
			FilterHelper.Filter<T> filter = (Filter<T>) arguments[0];
			queryWrapper.parameterInIdentifiers(filter.filterMasters(MovementCollection.class),MovementCollection.FIELD_BUFFER,GlobalIdentifier.FIELD_IDENTIFIER);
		}
	}
	
	@Override
	public Collection<MovementCollection> readByTypeByJoin(MovementCollectionType type, AbstractIdentifiable join) {
		Collection<MovementCollectionIdentifiableGlobalIdentifier> movementCollectionIdentifiableGlobalIdentifiers 
			= inject(MovementCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(join);
		return MethodHelper.getInstance().callGet(CollectionHelper.getInstance().filter(movementCollectionIdentifiableGlobalIdentifiers, FieldHelper.getInstance().buildPath(
				MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION,MovementCollection.FIELD_TYPE), type),MovementCollection.class
				,MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION);
	}
	
	@Override
	public Collection<MovementCollection> readByBuffer(MovementCollection movementCollection) {
		return readByFilter(new MovementCollection.Filter().addMaster(movementCollection));
	}
	
	@Override
	public Long countByBuffer(MovementCollection movementCollection) {
		return countByFilter(new MovementCollection.Filter().addMaster(movementCollection));
	}
}
 