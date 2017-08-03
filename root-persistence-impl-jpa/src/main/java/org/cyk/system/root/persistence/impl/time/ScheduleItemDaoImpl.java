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
import org.cyk.utility.common.helper.TimeHelper;

public class ScheduleItemDaoImpl extends AbstractCollectionItemDaoImpl<ScheduleItem,Schedule> implements ScheduleItemDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readWhereFromBetween,countWhereFromBetween;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		StructuredQueryLanguageHelper structureQueryLanguageHelper = StructuredQueryLanguageHelper.getInstance();
		System.out.println( _select().whereString(structureQueryLanguageHelper.getBetween("r.instantInterval.from.year", ":fromYear", ":toYear")
				+ " AND "+structureQueryLanguageHelper.getBetween("r.instantInterval.from.monthOfYear", ":fromMonthOfYear", ":toMonthOfYear")
				+ " AND "+structureQueryLanguageHelper.getBetween("r.instantInterval.from.dayOfMonth", ":fromDayOfMonth", ":toDayOfMonth")));
		registerNamedQuery(readWhereFromBetween, _select().whereString(structureQueryLanguageHelper.getBetween("r.instantInterval.from.year", ":fromYear", ":toYear")
				+ " AND "+structureQueryLanguageHelper.getBetween("r.instantInterval.from.monthOfYear", ":fromMonthOfYear", ":toMonthOfYear")
				+ " AND "+structureQueryLanguageHelper.getBetween("r.instantInterval.from.dayOfMonth", ":fromDayOfMonth", ":toDayOfMonth")));
	}
	
	@Override
	public Collection<ScheduleItem> readWhereFromBetween(Date from, Date to) {
		return namedQuery(readWhereFromBetween)
				.parameter("fromYear", TimeHelper.getInstance().getYear(from)).parameter("toYear", TimeHelper.getInstance().getYear(to))
				.parameter("fromMonthOfYear", TimeHelper.getInstance().getMonthOfYear(from)).parameter("toMonthOfYear", TimeHelper.getInstance().getMonthOfYear(to))
				.parameter("fromDayOfMonth", TimeHelper.getInstance().getDayOfMonth(from)).parameter("toDayOfMonth", TimeHelper.getInstance().getDayOfMonth(to))
				.resultMany();
	}

	@Override
	public Long countWhereFromBetween(Date from, Date to) {
		return countNamedQuery(countWhereFromBetween).parameter(InstantInterval.FIELD_FROM, from).parameter(InstantInterval.FIELD_TO, to).resultOne();
	}
	
}
 