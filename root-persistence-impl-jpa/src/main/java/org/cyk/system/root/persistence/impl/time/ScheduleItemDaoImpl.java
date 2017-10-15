package org.cyk.system.root.persistence.impl.time;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.persistence.api.time.ScheduleItemDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper;

public class ScheduleItemDaoImpl extends AbstractCollectionItemDaoImpl<ScheduleItem,Schedule> implements ScheduleItemDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readWhereFromBetween,countWhereFromBetween;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();		
		registerNamedQuery(readWhereFromBetween, StructuredQueryLanguageHelper.getInstance().getBuilder(clazz).setFieldName("instantInterval")
				.where().lp().bw("from.date").or().bw("to.date").rp().or().lp().lte("from.date","fromDate").and().gte("to.date","toDate").rp().getParent().execute());
	}
	
	@Override
	public Collection<ScheduleItem> readWhereFromBetween(Date from, Date to) {
		return namedQuery(readWhereFromBetween).parameter("fromDate", from).parameter("toDate", to).resultMany();
	}

	@Override
	public Long countWhereFromBetween(Date from, Date to) {
		return countNamedQuery(countWhereFromBetween).parameter("fromDate", from).parameter("toDate", to).resultOne();
	}
	
}
 