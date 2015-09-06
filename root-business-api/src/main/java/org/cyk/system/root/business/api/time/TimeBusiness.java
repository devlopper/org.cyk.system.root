package org.cyk.system.root.business.api.time;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.time.TimeDivisionType;

public interface TimeBusiness {

	String DATE_SHORT_PATTERN = "dd/MM/yyyy";
	String DATE_LONG_PATTERN = "EEEE , dd/MM/yyyy";
	String DATE_TIME_SHORT_PATTERN = "dd/MM/yyyy à HH:mm";
	String DATE_TIME_LONG_PATTERN = "EEEE , dd/MM/yyyy à HH:mm";
	String TIME_SHORT_PATTERN = "HH:mm";
	
	String format(Field field,Date date);
	
	String formatDate(Date date,String pattern,Locale locale);
	String formatDate(Date fromDate,Date toDate,String pattern,Locale locale);
	
	String formatDate(Date date,String pattern);
	String formatDate(Date fromDate,Date toDate,String pattern);
	String formatDate(Date date,Locale locale);
	String formatDate(Date fromDate,Date toDate,Locale locale);
	
	String formatDate(Date date);
	String formatDate(Date fromDate,Date toDate);
	
	String formatDateTime(Date date,Locale locale);
	String formatDateTime(Date fromDate,Date toDate,Locale locale);
	String formatDateTime(Date date);
	String formatDateTime(Date fromDate,Date toDate);
	
	String formatTime(Date time,Locale locale);
	String formatTime(Date time);
	
	String findFormatPattern(Field field);
	
	Date findUniversalTimeCoordinated();
	
	Long findDuration(Period period);
	
	Long findDuration(Collection<Period> periods);
	
	String formatDuration(Long durationInMillisecond);
	
	Collection<Period> findPeriods(Long fromDate,Long toDate,Long duration);
	
	Collection<Period> findPeriods(Period period,TimeDivisionType timeDivisionType,Boolean partial);
	
	Boolean between(Period period,Date date,Boolean excludeFromDate,Boolean excludeToDate);
	
	String formatPeriod(Period period,TimeDivisionType timeDivisionType);
	String formatPeriodFromTo(Period period,String pattern);
	String formatPeriodFromTo(Period period);

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
	
	Date findWithTimeAtStartOfTheDay(Date date);
	
	Date findWithTimeAtEndOfTheDay(Date date);
	
	Set<Integer> findMonthIndexes(Period period);
}
