package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.time.ScheduleBusiness;
import org.cyk.system.root.business.api.time.ScheduleItemBusiness;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.persistence.api.time.ScheduleItemDao;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.TimeHelper;

public class ScheduleBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

	@Override
	protected void businesses() {
		TestCase testCase = instanciateTestCase();
		testCase.create(inject(ScheduleBusiness.class).instanciateOne("S001", "My schedule"));
		ScheduleItem scheduleItem = inject(ScheduleItemBusiness.class).instanciateOne(testCase.read(Schedule.class, "S001"),"Maths", "Cours de maths");
		scheduleItem.setCode("MATHS");
		scheduleItem.getInstantInterval().getFrom().set(2005, 1, 2, 1, 7, 30, 0, 0);
		scheduleItem.getInstantInterval().getTo().set(2005, 3, 2, 1, 7, 30, 0, 0);
		scheduleItem.getInstantInterval().setPortionInMillisecond(5400000l);
		testCase.create(scheduleItem);
		
		scheduleItem = inject(ScheduleItemBusiness.class).instanciateOne(testCase.read(Schedule.class, "S001"),"Biologie", "Cours de biologie");
		scheduleItem.setCode("BIO");
		scheduleItem.getInstantInterval().getFrom().set(2005, 4, 2, 1, 7, 30, 0, 0);
		scheduleItem.getInstantInterval().getTo().set(2005, 6, 2, 1, 7, 30, 0, 0);
		scheduleItem.getInstantInterval().setPortionInMillisecond(5400000l);
		testCase.create(scheduleItem);
		
		scheduleItem = inject(ScheduleItemBusiness.class).instanciateOne(testCase.read(Schedule.class, "S001"),"Sciences", "Cours de sciences");
		scheduleItem.setCode("SCI");
		scheduleItem.getInstantInterval().getFrom().set(2005, 1, 5, 1, 7, 30, 0, 0);
		scheduleItem.getInstantInterval().getTo().set(2005, 6, 5, 1, 7, 30, 0, 0);
		scheduleItem.getInstantInterval().setPortionInMillisecond(5400000l);
		testCase.create(scheduleItem);
		
		//AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2005, 1, 2),testCase.read(ScheduleItem.class, "S001_MATHS").getInstantInterval().getFrom());
		
		AssertionHelper.getInstance().assertEquals(0l, inject(ScheduleItemDao.class).countWhereFromBetween(TimeHelper.getInstance().getDate(2004, 1, 1),TimeHelper.getInstance().getDate(2004, 3, 1)));
		//System.out.println("0 : "+inject(ScheduleItemDao.class).readWhereFromBetween(TimeHelper.getInstance().getDate(2004, 1, 1),TimeHelper.getInstance().getDate(2004, 3, 1)));
		
		AssertionHelper.getInstance().assertEquals(2l, inject(ScheduleItemDao.class).countWhereFromBetween(TimeHelper.getInstance().getDate(2004, 12, 1),TimeHelper.getInstance().getDate(2005, 1, 31)));
		AssertionHelper.getInstance().assertEquals(2l, inject(ScheduleItemDao.class).countWhereFromBetween(TimeHelper.getInstance().getDate(2005, 1, 1),TimeHelper.getInstance().getDate(2005, 1, 31)));
		AssertionHelper.getInstance().assertEquals(2l, inject(ScheduleItemDao.class).countWhereFromBetween(TimeHelper.getInstance().getDate(2005, 2, 1),TimeHelper.getInstance().getDate(2005, 2, 28)));
		AssertionHelper.getInstance().assertEquals(2l, inject(ScheduleItemDao.class).countWhereFromBetween(TimeHelper.getInstance().getDate(2005, 3, 1),TimeHelper.getInstance().getDate(2005, 3, 31)));
		AssertionHelper.getInstance().assertEquals(2l, inject(ScheduleItemDao.class).countWhereFromBetween(TimeHelper.getInstance().getDate(2005, 1, 1),TimeHelper.getInstance().getDate(2005, 3, 31)));
		//System.out.println("1 : "+inject(ScheduleItemDao.class).readWhereFromBetween(TimeHelper.getInstance().getDate(2005, 1, 1),TimeHelper.getInstance().getDate(2005, 3, 31)));
		
		AssertionHelper.getInstance().assertEquals(2l, inject(ScheduleItemDao.class).countWhereFromBetween(TimeHelper.getInstance().getDate(2005, 4, 1),TimeHelper.getInstance().getDate(2005, 6, 30)));
		//System.out.println("2 : "+inject(ScheduleItemDao.class).readWhereFromBetween(TimeHelper.getInstance().getDate(2005, 4, 1),TimeHelper.getInstance().getDate(2005, 6, 30)));
		
		AssertionHelper.getInstance().assertEquals(3l, inject(ScheduleItemDao.class).countWhereFromBetween(TimeHelper.getInstance().getDate(2005, 1, 1),TimeHelper.getInstance().getDate(2005, 6, 30)));
		//System.out.println("3 : "+inject(ScheduleItemDao.class).readWhereFromBetween(TimeHelper.getInstance().getDate(2005, 1, 1),TimeHelper.getInstance().getDate(2005, 6, 30)));
	}
   
	//@Test
    public void t(){
    	
    }
    
}
