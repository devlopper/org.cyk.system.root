package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.mathematics.IntervalCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;

@Stateless
public class IntervalCollectionBusinessImpl extends AbstractCollectionBusinessImpl<IntervalCollection,Interval, IntervalCollectionDao,IntervalDao> implements IntervalCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private IntervalDao intervalDao;
	
	@Inject
	public IntervalCollectionBusinessImpl(IntervalCollectionDao dao) {
		super(dao); 
	}
		
	@Override
	protected IntervalDao getItemDao() {
		return intervalDao;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal findLowestValue(IntervalCollection intervalCollection) {
		return dao.readLowestValue(intervalCollection);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal findHighestValue(IntervalCollection intervalCollection) {
		return dao.readHighestValue(intervalCollection);
	}
	
	@Override
	protected void __load__(IntervalCollection collection) {
		super.__load__(collection);
		collection.setLowestValue(findLowestValue(collection));
		collection.setHighestValue(findHighestValue(collection));
	}

}
