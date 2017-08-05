package org.cyk.system.root.persistence.impl.time;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.persistence.api.time.ScheduleItemDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Where;

public class ScheduleItemDaoImpl extends AbstractCollectionItemDaoImpl<ScheduleItem,Schedule> implements ScheduleItemDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readWhereFromBetween,countWhereFromBetween;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();		
		StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage jpql = new StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage();
		Where where = new Where.Adapter.Default.JavaPersistenceQueryLanguage().setStructuredQueryLanguageBuilder(jpql);		
		where.leftParathensis().addBetween("r.instantInterval.from.date").or().addBetween("r.instantInterval.to.date").rightParathensis().or()
		.leftParathensis().addLessThanOrEqual("r.instantInterval.from.date","fromDate").and().addGreaterThanOrEqual("r.instantInterval.to.date","toDate").rightParathensis()
		;
		registerNamedQuery(readWhereFromBetween, jpql.addTupleCollection("ScheduleItem", "r").addWhere(where.execute()).execute());
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
 