package org.cyk.system.root.business.impl.unit;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.impl.time.TimeBusinessImpl;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;

public class TimeUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    //private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyy HH:mm:ss.SSS");
    
    private TimeBusinessImpl timeBusiness = new TimeBusinessImpl();
    
    @Override
    protected void _execute_() {
    	super._execute_();
    	Assert.assertEquals(1, timeBusiness.findNumberOfDaysIn(new Period(createDate(1, 1, 2000, 1, 0), createDate(1, 1, 2000, 1, 0)), Boolean.TRUE).intValue());
    	Assert.assertEquals(1, timeBusiness.findNumberOfDaysIn(new Period(createDate(1, 1, 2000, 0, 0), createDate(1, 1, 2000, 23, 0)), Boolean.TRUE).intValue());
    	Assert.assertEquals(1, timeBusiness.findNumberOfDaysIn(new Period(createDate(1, 1, 2000, 0, 0), createDate(1, 1, 2000, 23, 59)), Boolean.TRUE).intValue());
    	
    	Assert.assertEquals(2, timeBusiness.findNumberOfDaysIn(new Period(createDate(1, 1, 2000, 1, 0), createDate(2, 1, 2000, 0, 1)), Boolean.TRUE).intValue());
    	Assert.assertEquals(2, timeBusiness.findNumberOfDaysIn(new Period(createDate(1, 1, 2000, 23, 59), createDate(2, 1, 2000, 0, 1)), Boolean.TRUE).intValue());
    	
    	Assert.assertEquals(25, timeBusiness.findMinuteOfHour(createDate(1, 1, 2000, 2, 25)).intValue());
    	Assert.assertEquals(145, timeBusiness.findMinuteOfDay(createDate(1, 1, 2000, 2, 25)).intValue());
    	
    	//System.out.println(new DateTime(createDate(1, 1, 2000, 2, 25)));
    	//System.out.println(new DateTime(createDate(1, 1, 2000, 2, 25)).plusMinutes(35).minusSeconds(1));
    	/*
    	for(Period p : timeBusiness.findPeriods(new Period(createDate(1, 1, 2000, 1, 0), createDate(5, 1, 2000, 2, 30)), 
    			new TimeDivisionType(TimeDivisionType.DAY, null, (long)DateTimeConstants.MILLIS_PER_DAY, null),Boolean.TRUE))
    		System.out.println(SIMPLE_DATE_FORMAT.format(p.getFromDate())+" - "+SIMPLE_DATE_FORMAT.format(p.getToDate()));
    	*/
    	/*
    	assertEqualsPeriod(new Period(createDate(1, 1, 2000, 1, 0), createDate(5, 1, 2000, 2, 30)),
    			new TimeDivisionType(RootConstant.Code.TimeDivisionType.DAY, null, (long)DateTimeConstants.MILLIS_PER_DAY, null), Boolean.TRUE,
    			new Date[]{createDate(1, 1, 2000, 1, 0),createDate(1, 1, 2000, 23, 59,59,999)}
    			,new Date[]{createDate(2, 1, 2000, 0, 0),createDate(2, 1, 2000, 23, 59,59,999)}
		    	,new Date[]{createDate(3, 1, 2000, 0, 0),createDate(3, 1, 2000, 23, 59,59,999)}
		    	,new Date[]{createDate(4, 1, 2000, 0, 0),createDate(4, 1, 2000, 23, 59,59,999)}
		    	,new Date[]{createDate(5, 1, 2000, 0, 0),createDate(5, 1, 2000, 2, 30)}
    			);
    	
    	for(Period period : timeBusiness.findPeriods(new Period(createDate(15, 1, 2015, 10, 00, 23, 0), createDate(21, 1, 2015, 17, 30, 0,0)), 
    			new TimeDivisionType(TimeDivisionType.MONTH, null, (long)DateTimeConstants.MILLIS_PER_DAY, null), Boolean.TRUE))
    		System.out.println(period);
    	
    	for(Period period : timeBusiness.findPeriods(new Period(createDate(15, 1, 2015, 10, 00, 23, 0), createDate(3, 4, 2015, 17, 30, 0,0)), 
    			new TimeDivisionType(TimeDivisionType.MONTH, null, (long)DateTimeConstants.MILLIS_PER_DAY, null), Boolean.TRUE))
    		System.out.println(period);
    	*/
    	System.out.println("Start of day : "+timeBusiness.findWithTimeAtStartOfTheDay(new Date()));
    	System.out.println("End of day   : "+timeBusiness.findWithTimeAtEndOfTheDay(new Date()));
    }
    
    private Date createDate(int d,int m,int y,int h,int mm){
    	return createDate(d, m, y, h, mm, 0, 0);
    }
     
    private Date createDate(int d,int m,int y,int h,int mm,int ss,int t){
		try {
			return DateUtils.parseDate(d+"/"+m+"/"+y+" "+h+":"+mm+":"+ss+"."+t, "dd/MM/yyyy HH:mm:ss.SSS");
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
    /*
    private void assertEqualsPeriod(Period period,TimeDivisionType timeDivisionType,Boolean partial,Date[]...expecteds){
    	int i=0;
    	for(Period p : timeBusiness.findPeriods(period, timeDivisionType,partial)){
    		//System.out.println(SIMPLE_DATE_FORMAT.format(p.getFromDate())+" - "+SIMPLE_DATE_FORMAT.format(p.getToDate()));
    		//System.out.println(expecteds[i][0].getTime()+" - "+SIMPLE_DATE_FORMAT.format(expecteds[i][0]));
    		//System.out.println(p.getFromDate().getTime()+" - "+SIMPLE_DATE_FORMAT.format(p.getFromDate()));
    		Assert.assertEquals("From Date",expecteds[i][0], p.getFromDate());
    		//System.out.println(expecteds[i][1].getTime()+" - "+SIMPLE_DATE_FORMAT.format(expecteds[i][1]));
    		//System.out.println(p.getToDate().getTime()+" - "+SIMPLE_DATE_FORMAT.format(p.getToDate()));
    		Assert.assertEquals("To Date",expecteds[i][1], p.getToDate());
    		
    		i++;
    	}
    		
    }*/

}
