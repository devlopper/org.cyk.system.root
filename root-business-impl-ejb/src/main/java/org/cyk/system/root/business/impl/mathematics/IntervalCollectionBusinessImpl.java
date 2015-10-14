package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.mathematics.IntervalCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;

public class IntervalCollectionBusinessImpl extends AbstractTypedBusinessService<IntervalCollection, IntervalCollectionDao> implements IntervalCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private IntervalDao intervalDao;
	
	@Inject
	public IntervalCollectionBusinessImpl(IntervalCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	protected void __load__(IntervalCollection identifiable) {
		super.__load__(identifiable);
		identifiable.setIntervals(intervalDao.readByCollection(identifiable));
	}

}
