package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.cdi.AbstractBean;

public class TimeBusinessImpl extends AbstractBean implements TimeBusiness,Serializable {

	private static final long serialVersionUID = -854697735401050272L;

	@Override
	public Long findDuration(Period period) {
		return period.getToDate().getTime()-period.getFromDate().getTime();
	}
	
	@Override
	public Long findDuration(Collection<Period> periods) {
		Long sum = 0l;
		for(Period period : periods)
			sum += findDuration(period);
		return sum;
	}

	@Override
	public String formatDuration(Long durationInMillisecond) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
