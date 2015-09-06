package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalManagerBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.mathematics.IntervalManagerDao;

public class IntervalManagerBusinessImpl extends AbstractTypedBusinessService<IntervalCollection, IntervalManagerDao> implements IntervalManagerBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public IntervalManagerBusinessImpl(IntervalManagerDao dao) {
		super(dao); 
	}

}
