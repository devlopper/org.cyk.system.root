package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class TimeBusinessImpl extends AbstractBean implements TimeBusiness,Serializable {

	private static final long serialVersionUID = -854697735401050272L;

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
			}
			
			if(firstPeriod!=null) periods.add(firstPeriod);
			if(!middlePeriods.isEmpty()) periods.addAll(middlePeriods);
			if(lastPeriod!=null) periods.add(lastPeriod);
			
			return periods;
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
		case TimeDivisionType.DAY:return DATE_SHORT_FORMAT.format(period.getFromDate());
		}
		return null;
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

}
