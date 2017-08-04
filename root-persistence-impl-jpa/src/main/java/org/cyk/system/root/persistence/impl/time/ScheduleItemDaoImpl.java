package org.cyk.system.root.persistence.impl.time;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.time.InstantInterval;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.persistence.api.time.ScheduleItemDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Where.Between;
import org.cyk.utility.common.helper.TimeHelper;

public class ScheduleItemDaoImpl extends AbstractCollectionItemDaoImpl<ScheduleItem,Schedule> implements ScheduleItemDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readWhereFromBetween,countWhereFromBetween;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		
		StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage jpql = new StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage();
		StructuredQueryLanguageHelper.Where.Between between = new StructuredQueryLanguageHelper.Where.Between.Adapter.Default.JavaPersistenceQueryLanguage().setStructuredQueryLanguageBuilder(jpql);
		
		String where = "("+between.setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.from.year").execute()
				+" AND "+between.clear().setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.from.monthOfYear").execute()
				+" AND "+between.clear().setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.from.dayOfMonth").execute()
				+")"
				+ " OR"
				+ " ("+between.clear().setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.to.year").execute()
				+" AND "+between.clear().setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.to.monthOfYear").execute()
				+" AND "+between.clear().setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.to.dayOfMonth").execute()
				
				+")"
				//+ " OR (  )"
				;
		
		registerNamedQuery(readWhereFromBetween, jpql.addTupleCollection("ScheduleItem", "r").addWhere(where).execute());
	}
	
	@Override
	public Collection<ScheduleItem> readWhereFromBetween(Date from, Date to) {
		return namedQuery(readWhereFromBetween)
				.parameter("fromYear", TimeHelper.getInstance().getYear(from)).parameter("toYear", TimeHelper.getInstance().getYear(to))
				.parameter("fromMonthOfYear", TimeHelper.getInstance().getMonthOfYear(from)).parameter("toMonthOfYear", TimeHelper.getInstance().getMonthOfYear(to))
				.parameter("fromDayOfMonth", TimeHelper.getInstance().getDayOfMonth(from)).parameter("toDayOfMonth", TimeHelper.getInstance().getDayOfMonth(to))
				/*
				.parameter("fromYear2", TimeHelper.getInstance().getYear(from)).parameter("toYear2", TimeHelper.getInstance().getYear(to))
				.parameter("fromMonthOfYear2", TimeHelper.getInstance().getMonthOfYear(from)).parameter("toMonthOfYear2", TimeHelper.getInstance().getMonthOfYear(to))
				.parameter("fromDayOfMonth2", TimeHelper.getInstance().getDayOfMonth(from)).parameter("toDayOfMonth2", TimeHelper.getInstance().getDayOfMonth(to))
				*/
				.resultMany();
	}

	@Override
	public Long countWhereFromBetween(Date from, Date to) {
		return countNamedQuery(countWhereFromBetween)
				.parameter("fromYear", TimeHelper.getInstance().getYear(from)).parameter("toYear", TimeHelper.getInstance().getYear(to))
				.parameter("fromMonthOfYear", TimeHelper.getInstance().getMonthOfYear(from)).parameter("toMonthOfYear", TimeHelper.getInstance().getMonthOfYear(to))
				.parameter("fromDayOfMonth", TimeHelper.getInstance().getDayOfMonth(from)).parameter("toDayOfMonth", TimeHelper.getInstance().getDayOfMonth(to))
				/*
				.parameter("fromYear2", TimeHelper.getInstance().getYear(from)).parameter("toYear2", TimeHelper.getInstance().getYear(to))
				.parameter("fromMonthOfYear2", TimeHelper.getInstance().getMonthOfYear(from)).parameter("toMonthOfYear2", TimeHelper.getInstance().getMonthOfYear(to))
				.parameter("fromDayOfMonth2", TimeHelper.getInstance().getDayOfMonth(from)).parameter("toDayOfMonth2", TimeHelper.getInstance().getDayOfMonth(to))
				*/				
				.resultOne();
	}
	
}
 