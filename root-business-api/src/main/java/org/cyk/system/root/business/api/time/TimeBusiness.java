package org.cyk.system.root.business.api.time;

import java.util.Collection;

import org.cyk.system.root.model.time.Period;

public interface TimeBusiness {

	Long findDuration(Period period);
	
	Long findDuration(Collection<Period> periods);
	
	String formatDuration(Long durationInMillisecond);
	
}
