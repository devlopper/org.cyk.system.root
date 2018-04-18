package org.cyk.system.root.persistence.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public class MovementCollectionIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<MovementCollectionIdentifiableGlobalIdentifier,MovementCollectionIdentifiableGlobalIdentifier.SearchCriteria> implements MovementCollectionIdentifiableGlobalIdentifierDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;

	@Override
	protected void listenInstanciateJpqlBuilder(String name, JavaPersistenceQueryLanguage builder) {
		super.listenInstanciateJpqlBuilder(name, builder);
		if(readByFilter.equals(name)){
			builder.setFieldName(MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
		}
	}
			
	@SuppressWarnings("unchecked")
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass,QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName,arguments);
		if(ArrayUtils.contains(new String[]{readByFilter,countByFilter}, queryName)){
			FilterHelper.Filter<T> filter = (Filter<T>) arguments[0];
			queryWrapper.parameterInIdentifiers(filter.filterMasters(MovementCollection.class),MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION,GlobalIdentifier.FIELD_IDENTIFIER);
		}
	}
	
	@Override
	public Collection<MovementCollectionIdentifiableGlobalIdentifier> readByMovementCollection(MovementCollection movementCollection) {
		return readByFilter(new MovementCollectionIdentifiableGlobalIdentifier.Filter().addMaster(movementCollection));
	}
	
	
}
 