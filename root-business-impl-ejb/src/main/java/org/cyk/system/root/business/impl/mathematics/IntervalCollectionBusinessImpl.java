package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.mathematics.IntervalCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;
import org.cyk.utility.common.generator.RandomDataProvider;

public class IntervalCollectionBusinessImpl extends AbstractCollectionBusinessImpl<IntervalCollection,Interval, IntervalCollectionDao,IntervalDao,IntervalBusiness> implements IntervalCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public IntervalCollectionBusinessImpl(IntervalCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	protected void afterCrud(IntervalCollection intervalCollection, Crud crud) {
		super.afterCrud(intervalCollection, crud);
		if(Crud.isCreateOrUpdate(crud)){
			intervalCollection.setLowestValue(findLowestValue(intervalCollection));
			intervalCollection.setHighestValue(findHighestValue(intervalCollection));
			dao.update(intervalCollection);
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public BigDecimal findLowestValue(IntervalCollection intervalCollection) {
		return dao.readLowestValue(intervalCollection);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public BigDecimal findHighestValue(IntervalCollection intervalCollection) {
		return dao.readHighestValue(intervalCollection);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean isAllIntervalLowerEqualsToHigher(IntervalCollection intervalCollection) {
		for(Interval interval : getItemDao().readByCollection(intervalCollection, Boolean.TRUE))
			if( !Boolean.TRUE.equals(inject(IntervalBusiness.class).isLowerEqualsToHigher(interval)) )
				return Boolean.FALSE;
		return Boolean.TRUE;
	}
	
	@Override
	public BigDecimal generateRandomValue(IntervalCollection intervalCollection) {
		return new BigDecimal(RandomDataProvider.getInstance().randomInt(intervalCollection.getLowestValue().intValue(), intervalCollection.getHighestValue().intValue()));
	}
	
	@Override
	protected void __load__(IntervalCollection collection) {
		super.__load__(collection);
		collection.setLowestValue(findLowestValue(collection));
		collection.setHighestValue(findHighestValue(collection));
	}

}
