package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public class MovementDaoImpl extends AbstractCollectionItemDaoImpl<Movement,MovementCollection> implements MovementDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
		
	@Override
	protected void processQueryStringBuilder(QueryStringBuilder queryStringBuilder, String queryName) {
		super.processQueryStringBuilder(queryStringBuilder, queryName);
		if(readWhereExistencePeriodFromDateIsLessThan.equals(queryName)){
			queryStringBuilder.and(Movement.FIELD_COLLECTION);
		}
	}
		
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass,QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName,arguments);
		if(readWhereExistencePeriodFromDateIsLessThan.equals(queryName)){
			Movement movement = (Movement) arguments[0];
			queryWrapper.parameter(Movement.FIELD_COLLECTION, movement.getCollection());
		}
	}
}
 