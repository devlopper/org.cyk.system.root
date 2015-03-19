package org.cyk.system.root.business.api.time;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.time.TimeDivisionType;

public interface TimeBusiness {

	SimpleDateFormat DATE_SHORT_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat DATE_LONG_FORMAT = new SimpleDateFormat("EEEE , dd/MM/yyyy");
	SimpleDateFormat DATE_TIME_SHORT_FORMAT = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
	SimpleDateFormat DATE_TIME_LONG_FORMAT = new SimpleDateFormat("EEEE , dd/MM/yyyy à HH:mm");
	
	Date findUniversalTimeCoordinated();
	
	Long findDuration(Period period);
	
	Long findDuration(Collection<Period> periods);
	
	String formatDuration(Long durationInMillisecond);
	
	Collection<Period> findPeriods(Long fromDate,Long toDate,Long duration);
	
	Collection<Period> findPeriods(Period period,TimeDivisionType timeDivisionType,Boolean partial);
	
	Boolean between(Period period,Date date,Boolean excludeFromDate,Boolean excludeToDate);
	
	String formatPeriod(Period period,TimeDivisionType timeDivisionType);

	Integer findNumberOfDaysIn(Period period, Boolean partial);
	
	Integer findDayOfWeek(Date date);
	Integer findDayOfMonth(Date date);
	Integer findDayOfYear(Date date);
	
	Integer findMonth(Date date);
	Integer findYear(Date date);
	
	Integer findHour(Date date);
	Integer findMinuteOfDay(Date date);
	Integer findMinuteOfHour(Date date);
	
	Integer findMillisecondOfDay(Date date);
}
