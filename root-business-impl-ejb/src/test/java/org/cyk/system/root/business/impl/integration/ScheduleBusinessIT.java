package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.time.ScheduleBusiness;
import org.cyk.system.root.business.api.time.ScheduleItemBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.persistence.api.time.ScheduleItemDao;
import org.cyk.utility.common.helper.TimeHelper;

public class ScheduleBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

	@Override
	protected void businesses() {
		TestCase testCase = instanciateTestCase();
		testCase.create(inject(ScheduleBusiness.class).instanciateOne("S001", "My schedule"));
		ScheduleItem scheduleItem = inject(ScheduleItemBusiness.class).instanciateOne(testCase.read(Schedule.class, "S001"),"Maths", "Cours de maths");
		scheduleItem.getInstantInterval().getFrom().set(2005, 1, 2, 1, 7, 30, 0, 0);
		scheduleItem.getInstantInterval().getTo().set(2005, 3, 2, 1, 7, 30, 0, 0);
		testCase.create(scheduleItem);
		
		scheduleItem = inject(ScheduleItemBusiness.class).instanciateOne(testCase.read(Schedule.class, "S001"),"Biologie", "Cours de biologie");
		scheduleItem.getInstantInterval().getFrom().set(2005, 4, 2, 1, 7, 30, 0, 0);
		scheduleItem.getInstantInterval().getTo().set(2005, 6, 2, 1, 7, 30, 0, 0);
		testCase.create(scheduleItem);
		
		scheduleItem = inject(ScheduleItemBusiness.class).instanciateOne(testCase.read(Schedule.class, "S001"),"Sciences", "Cours de sciences");
		scheduleItem.getInstantInterval().getFrom().set(2005, 1, 5, 1, 7, 30, 0, 0);
		scheduleItem.getInstantInterval().getTo().set(2005, 6, 5, 1, 7, 30, 0, 0);
		testCase.create(scheduleItem);
		
		System.out.println("0 : "+inject(ScheduleItemDao.class).readWhereFromBetween(TimeHelper.getInstance().getDate(2004, 1, 1),TimeHelper.getInstance().getDate(2004, 3, 1)));
		
		System.out.println("1 : "+inject(ScheduleItemDao.class).readWhereFromBetween(TimeHelper.getInstance().getDate(2005, 1, 1),TimeHelper.getInstance().getDate(2005, 3, 31)));
		
		System.out.println("2 : "+inject(ScheduleItemDao.class).readWhereFromBetween(TimeHelper.getInstance().getDate(2005, 4, 1),TimeHelper.getInstance().getDate(2005, 6, 30)));
		
		System.out.println("3 : "+inject(ScheduleItemDao.class).readWhereFromBetween(TimeHelper.getInstance().getDate(2005, 1, 1),TimeHelper.getInstance().getDate(2005, 6, 30)));
	}
   
	//@Test
    public void t(){
    	
    }
    
}
