package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.IdentifiablePeriodBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodDao;
import org.cyk.utility.common.helper.LoggingHelper;

public class IdentifiablePeriodBusinessImpl extends AbstractTypedBusinessService<IdentifiablePeriod, IdentifiablePeriodDao> implements IdentifiablePeriodBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public IdentifiablePeriodBusinessImpl(IdentifiablePeriodDao dao) {
		super(dao); 
	}
	
	@Override
	protected void computeChanges(IdentifiablePeriod identifiablePeriod, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(identifiablePeriod, loggingMessageBuilder);
		
	}
	
}