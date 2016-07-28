package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class PeriodDetails implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private String fromDate,toDate;
	
	public PeriodDetails(Period period) {
		fromDate = RootBusinessLayer.getInstance().getTimeBusiness().formatDateTime(period.getFromDate());
		toDate = RootBusinessLayer.getInstance().getTimeBusiness().formatDateTime(period.getToDate());
	}
}
