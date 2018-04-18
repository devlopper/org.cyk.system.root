package org.cyk.system.root.persistence.impl.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementAction;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementMode;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public class MovementDaoImpl extends AbstractCollectionItemDaoImpl<Movement,MovementCollection> implements MovementDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected void listenInstanciateJpqlBuilder(String name, JavaPersistenceQueryLanguage builder) {
		super.listenInstanciateJpqlBuilder(name, builder);
		if(readByFilter.equals(name)){
			builder.setFieldName(Movement.FIELD_ACTION).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
			builder.setFieldName(Movement.FIELD_MODE).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
			builder.setFieldName(Movement.FIELD_PARENT).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
		}
	}
			
	@SuppressWarnings("unchecked")
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass,QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName,arguments);
		if(ArrayUtils.contains(new String[]{readByFilter,countByFilter}, queryName)){
			FilterHelper.Filter<T> filter = (Filter<T>) arguments[0];
			queryWrapper.parameterInIdentifiers(filter.filterMasters(MovementAction.class),Movement.FIELD_ACTION,GlobalIdentifier.FIELD_IDENTIFIER);
			queryWrapper.parameterInIdentifiers(filter.filterMasters(MovementMode.class),Movement.FIELD_MODE,GlobalIdentifier.FIELD_IDENTIFIER);
			queryWrapper.parameterInIdentifiers(filter.filterMasters(Movement.class),Movement.FIELD_PARENT,GlobalIdentifier.FIELD_IDENTIFIER);
		}
	}

	@Override
	public BigDecimal sumValueWhereExistencePeriodFromDateIsLessThan(Movement movement) {
		Collection<Movement> movements = readWhereExistencePeriodFromDateIsLessThan(movement);
		BigDecimal sum = BigDecimal.ZERO;
		for(Movement m : movements)
			sum = sum.add(m.getValue());
		return sum;
	}
	
	@Override
	public BigDecimal sumValueByCollection(MovementCollection movementCollection) {
		Collection<Movement> movements = readByCollection(movementCollection);
		BigDecimal sum = BigDecimal.ZERO;
		for(Movement index : movements)
			sum = sum.add(index.getValue());
		return sum;
	}
	
}
 