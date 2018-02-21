package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.IdentifiablePeriodBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodDao;

public class IdentifiablePeriodBusinessImpl extends AbstractTypedBusinessService<IdentifiablePeriod, IdentifiablePeriodDao> implements IdentifiablePeriodBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public IdentifiablePeriodBusinessImpl(IdentifiablePeriodDao dao) {
		super(dao); 
	}
		
}