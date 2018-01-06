package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public class MovementDaoImpl extends AbstractCollectionItemDaoImpl<Movement,MovementCollection> implements MovementDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	//private String sumValueWhereExistencePeriodFromDateIsLessThan;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		/*
		String dateFieldName = commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE);
		registerNamedQuery(sumValueWhereExistencePeriodFromDateIsLessThan, _select().where(dateFieldName,Period.FIELD_FROM_DATE,ArithmeticOperator.LT)
				.and(AbstractIdentifiable.FIELD_IDENTIFIER, ArithmeticOperator.NEQ));
		*/
	}
	
	@Override
	protected void listenInstanciateJpqlBuilder(String name, JavaPersistenceQueryLanguage builder) {
		super.listenInstanciateJpqlBuilder(name, builder);
		if(readByFilter.equals(name)){
			builder.setFieldName(Movement.FIELD_COLLECTION).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
			builder.setFieldName(Movement.FIELD_ACTION).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
		}else if(readWhereExistencePeriodFromDateIsLessThan.equals(name) || countWhereExistencePeriodFromDateIsLessThan.equals(name)){
			builder.where().and().eq(Movement.FIELD_COLLECTION);
		}
	}
			
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass,QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName,arguments);
		if(ArrayUtils.contains(new String[]{readByFilter,countByFilter}, queryName)){
			FilterHelper.Filter<T> filter = (Filter<T>) arguments[0];
			queryWrapper.parameterInIdentifiers(filter.filterMasters(MovementCollection.class),Movement.FIELD_COLLECTION,GlobalIdentifier.FIELD_IDENTIFIER);
			queryWrapper.parameterInIdentifiers(filter.filterMasters(MovementAction.class),Movement.FIELD_ACTION,GlobalIdentifier.FIELD_IDENTIFIER); 
		}else if(readWhereExistencePeriodFromDateIsLessThan.equals(queryName) || countWhereExistencePeriodFromDateIsLessThan.equals(queryName)){
			Movement movement = (Movement) arguments[0];
			queryWrapper.parameter(Movement.FIELD_COLLECTION, movement.getCollection());
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
	
}
 