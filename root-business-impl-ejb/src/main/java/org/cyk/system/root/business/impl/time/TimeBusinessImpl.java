package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.cdi.AbstractBean;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class TimeBusinessImpl extends AbstractBean implements TimeBusiness,Serializable {

	private static final long serialVersionUID = -854697735401050272L;

	private LanguageBusiness languageBusiness = LanguageBusinessImpl.getInstance();
	
	public String format(Field field,Date date){
		if(date==null)
			return Constant.EMPTY_STRING;
		return new SimpleDateFormat(findFormatPattern(field)).format(date);
	}
	
	@Override
	public String formatDate(Date date,String pattern,Locale locale) {
		if(date==null)
			return Constant.EMPTY_STRING;
		return new SimpleDateFormat(pattern, locale).format(date);
	}
	@Override
	public String formatDate(Date date, String pattern) {
		return formatDate(date, pattern, languageBusiness.findCurrentLocale());
	}
	@Override
	public String formatDate(Date date, Locale locale) {
		return formatDate(date, TimeBusiness.DATE_SHORT_PATTERN,locale);
	}
	@Override
	public String formatDate(Date date) {
		return formatDate(date, languageBusiness.findCurrentLocale());
	}
	
	@Override
	public String formatDateTime(Date date, Locale locale) {
		return formatDate(date, TimeBusiness.DATE_TIME_SHORT_PATTERN,locale);
	}
	@Override
	public String formatDateTime(Date date) {
		return formatDateTime(date,languageBusiness.findCurrentLocale());
	}
	
	@Override
	public String formatTime(Date time, Locale locale) {
		return formatDate(time, TimeBusiness.TIME_SHORT_PATTERN,locale);
	}
	@Override
	public String formatTime(Date time) {
		return formatTime(time,languageBusiness.findCurrentLocale());
	}
	
	@Override
	public String findFormatPattern(Field field) {
		InputCalendar inputCalendar = field.getAnnotation(InputCalendar.class);
		if(inputCalendar==null || InputCalendar.Format.AUTO.equals(inputCalendar.format())){
			Temporal temporal = field.getAnnotation(Temporal.class);
			if(temporal==null || TemporalType.DATE.equals(temporal.value()))
				return TimeBusiness.DATE_SHORT_PATTERN;
			else if(TemporalType.TIME.equals(temporal.value()))
				return TimeBusiness.TIME_SHORT_PATTERN;
			else
				return TimeBusiness.DATE_TIME_SHORT_PATTERN;
		}else
			switch(inputCalendar.format()){
			case DATE_SHORT:return TimeBusiness.DATE_SHORT_PATTERN;
			case DATE_LONG:return TimeBusiness.DATE_LONG_PATTERN;
			case DATETIME_SHORT:return TimeBusiness.DATE_TIME_SHORT_PATTERN;
			case DATETIME_LONG:return TimeBusiness.DATE_TIME_LONG_PATTERN;
			default:return null;
			}
	}
	
	@Override
	public Date findUniversalTimeCoordinated() {
		return commonUtils.getUniversalTimeCoordinated();
	}
	
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
		return null;
	}

	@Override
	public Collection<Period> findPeriods(Period period,TimeDivisionType timeDivisionType,Boolean partial) {
		if(Boolean.TRUE.equals(partial)){
			Period firstPeriod=null,lastPeriod=null;
			Collection<Period> middlePeriods = new ArrayList<>(),periods = new ArrayList<>();
			Date fromDate=period.getFromDate(),toDate=period.getToDate();
			
			if(timeDivisionType.getCode().equals(TimeDivisionType.DAY)){
				Integer milliseconds = findMillisecondOfDay(fromDate);
				if(milliseconds>0){
					milliseconds = DateTimeConstants.MILLIS_PER_DAY - milliseconds - 1;//remaining to go to next day - be at the limit by doing minus one
					firstPeriod = new Period(fromDate,new DateTime(fromDate).plusMillis(milliseconds).toDate());
					fromDate = new DateTime(fromDate).plusMillis(milliseconds+1).toDate();// plus one to avoid crossing previous period
				}
				
				milliseconds = findMillisecondOfDay(toDate);
				if(milliseconds>0){
					lastPeriod = new Period(new DateTime(toDate).minusMillis(milliseconds).toDate(),toDate);
					toDate = new DateTime(toDate).minusMillis(milliseconds-1).toDate();// plus one to avoid crossing previous period
				}
				
				Integer dayIndex = findDayOfYear(fromDate),lastDayIndex=findDayOfYear(toDate);
				while(dayIndex++<lastDayIndex){
					middlePeriods.add(new Period(fromDate, new DateTime(fromDate).plus(DateTimeConstants.MILLIS_PER_DAY-1).toDate()));
					fromDate = new DateTime(fromDate).plus(DateTimeConstants.MILLIS_PER_DAY).toDate();
					//dayIndex++;
				}
			}else if(timeDivisionType.getCode().equals(TimeDivisionType.WEEK)){
				/*
				Integer milliseconds = findMillisecondOfDay(fromDate);
				if(milliseconds>0){
					milliseconds = DateTimeConstants.MILLIS_PER_DAY - milliseconds - 1;//remaining to go to next day - be at the limit by doing minus one
					firstPeriod = new Period(fromDate,new DateTime(fromDate).plusMillis(milliseconds).toDate());
					fromDate = new DateTime(fromDate).plusMillis(milliseconds+1).toDate();// plus one to avoid crossing previous period
				}
				
				milliseconds = findMillisecondOfDay(toDate);
				if(milliseconds>0){
					lastPeriod = new Period(new DateTime(toDate).minusMillis(milliseconds).toDate(),toDate);
					toDate = new DateTime(toDate).minusMillis(milliseconds-1).toDate();// plus one to avoid crossing previous period
				}
				
				Integer dayIndex = findDayOfYear(fromDate),lastDayIndex=findDayOfYear(toDate);
				while(dayIndex++<lastDayIndex){
					middlePeriods.add(new Period(fromDate, new DateTime(fromDate).plus(DateTimeConstants.MILLIS_PER_DAY-1).toDate()));
					fromDate = new DateTime(fromDate).plus(DateTimeConstants.MILLIS_PER_DAY).toDate();
					//dayIndex++;
				}
				*/
			}else if(timeDivisionType.getCode().equals(TimeDivisionType.MONTH)){
				Date begin = period.getFromDate(),end=null,index=begin;
				Integer beginMonth,endMonth = findMonth(period.getToDate());
				Boolean stop = Boolean.FALSE;
				do{
					beginMonth = findMonth(index);
					while(beginMonth==findMonth(index) && beginMonth!= endMonth)
						index = DateUtils.addDays(index, 1);
					
					if(beginMonth==endMonth){
						end = period.getToDate();
						stop = Boolean.TRUE;
					}else{
						index = DateUtils.addDays(index, -1);
						end = new DateTime(findYear(begin),beginMonth, findDayOfMonth(index), 23, 59, 59,999).toDate();
					}

					periods.add(new Period(begin, end));
					index = begin = DateUtils.addMilliseconds(end, 1);
					
				}while(!Boolean.TRUE.equals(stop));
			}
			
			if(firstPeriod!=null) periods.add(firstPeriod);
			if(!middlePeriods.isEmpty()) periods.addAll(middlePeriods);
			if(lastPeriod!=null) periods.add(lastPeriod);
			
			return periods;
		}else if(timeDivisionType.getCode().equals(TimeDivisionType.YEAR)){
			//TODO year periods
			return null;
		}else{
			return findPeriods(period.getFromDate().getTime(), period.getToDate().getTime(), period.getDuration());
		}
	}

	@Override
	public Collection<Period> findPeriods(Long fromDate,Long toDate,Long duration) {
		Integer numberOfPeriod = (int) ((toDate-fromDate)/duration);
		Collection<Period> periods = new ArrayList<>(numberOfPeriod);
		for(int i=0;i<numberOfPeriod;i++){
			periods.add(new Period(new Date(fromDate + duration*i), new Date(fromDate + duration*(i+1))));
		}
		return periods;
	}
	
	@Override
	public Boolean between(Period period, Date date,Boolean excludeFromDate,Boolean excludeToDate) {
		Long t1=period.getFromDate().getTime(),t2=period.getToDate().getTime(),t=date.getTime();
		if(Boolean.TRUE.equals(excludeFromDate))
			if(Boolean.TRUE.equals(excludeToDate))
				return t1<t && t<t2;
			else
				return t1<t && t<=t2;
		else
			if(Boolean.TRUE.equals(excludeToDate))
				return t1<=t && t<t2;
			else
				return t1<=t && t<=t2;
	}

	@Override
	public String formatPeriod(Period period, TimeDivisionType timeDivisionType) {
		switch(timeDivisionType.getCode()){
		case TimeDivisionType.DAY:return formatDate(period.getFromDate(),TimeBusiness.DATE_SHORT_PATTERN);
		case TimeDivisionType.MONTH:return new DateFormatSymbols(languageBusiness.findCurrentLocale()).getMonths()[findMonth(period.getFromDate())-1]
				/*+formatDate(period.getFromDate(),TimeBusiness.DATE_SHORT_PATTERN)*/;
		}
		return null;
	}
	
	@Override
	public String formatPeriodFromTo(Period period, String pattern) {
		return languageBusiness.findText("from.date")+" "+formatDate(period.getFromDate(),pattern)+" "
				+languageBusiness.findText("to.date")+"  "+formatDate(period.getToDate(),pattern);
	}
	
	@Override
	public String formatPeriodFromTo(Period period) {
		return formatPeriodFromTo(period, DATE_SHORT_PATTERN);
	}
	
	@Override
	public Integer findNumberOfDaysIn(Period period,Boolean partial){
		return new DateTime(period.getToDate()).getDayOfYear() - new DateTime(period.getFromDate()).getDayOfYear() + 1;
	}

	@Override
	public Integer findDayOfWeek(Date date) {
		return new DateTime(date).getDayOfWeek();
	}
	
	@Override
	public Integer findDayOfMonth(Date date) {
		return new DateTime(date).getDayOfMonth();
	}
	@Override
	public Integer findDayOfYear(Date date) {
		return new DateTime(date).getDayOfYear();
	}

	@Override
	public Integer findMonth(Date date) {
		return new DateTime(date).getMonthOfYear();
	}

	@Override
	public Integer findYear(Date date) {
		return new DateTime(date).getYear();
	}
	@Override
	public Integer findHour(Date date) {
		return new DateTime(date).getHourOfDay();
	}
	@Override
	public Integer findMinuteOfHour(Date date) {
		return new DateTime(date).getMinuteOfHour();
	}
	@Override
	public Integer findMinuteOfDay(Date date) {
		return new DateTime(date).getMinuteOfDay();
	}
	
	@Override
	public Integer findMillisecondOfDay(Date date) {
		return new DateTime(date).getMillisOfDay();
	}
	
	@Override
	public String formatDate(Date fromDate, Date toDate, String pattern,Locale locale) {
		if(findDayOfYear(fromDate)==findDayOfYear(toDate)){
			return formatDate(fromDate, DATE_SHORT_PATTERN, locale)+" , "+formatTime(fromDate, locale)+" - "+formatTime(toDate, locale);
		}else
			return formatDate(fromDate, pattern, locale)+" - "+formatDate(toDate, pattern, locale);
	}
	@Override
	public String formatDate(Date fromDate, Date toDate, String pattern) {
		return formatDate(fromDate,toDate,pattern,languageBusiness.findCurrentLocale());
	}
	@Override
	public String formatDate(Date fromDate, Date toDate, Locale locale) {
		return formatDate(fromDate,toDate,DATE_SHORT_PATTERN,locale);
	}
	@Override
	public String formatDate(Date fromDate, Date toDate) {
		return formatDate(fromDate,toDate,DATE_SHORT_PATTERN);
	}
	@Override
	public String formatDateTime(Date fromDate, Date toDate, Locale locale) {
		return formatDate(fromDate, toDate, DATE_TIME_SHORT_PATTERN, locale);
	}
	@Override
	public String formatDateTime(Date fromDate, Date toDate) {
		return formatDateTime(fromDate, toDate, languageBusiness.findCurrentLocale());
	}

	@Override
	public Date findWithTimeAtStartOfTheDay(Date date) {
		return new DateTime(date).withTimeAtStartOfDay().toDate();
	}

	@Override
	public Date findWithTimeAtEndOfTheDay(Date date) {
		return new DateTime(date).withTime(23, 59, 59, 999).toDate();
	}

	@Override
	public Set<Integer> findMonthIndexes(Period period) {
		Set<Integer> indexes = new LinkedHashSet<>();
		Integer index=findMonth(period.getFromDate()),endIndex=findMonth(period.getToDate());
		do{
			indexes.add(index);
			index++;
		}while(index!=endIndex);
		return indexes;
	}
	
	@Override
	public Date parse(String date) {
		try {
			return new SimpleDateFormat(DATE_SHORT_PATTERN, languageBusiness.findCurrentLocale()).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
