package org.cyk.system.root.business.api.time;

import java.util.Date;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.time.DurationType;

public interface DurationTypeBusiness extends AbstractEnumerationBusiness<DurationType> {
 
	Long computeNumberOfMillisecond(DurationType durationType,Date expectedFromDate,Date expectedToDate,Date currentFromDate);
	
	Long computeNumberOfMillisecond(DurationType durationType,Date expectedFromDate,Date expectedToDate);
}
