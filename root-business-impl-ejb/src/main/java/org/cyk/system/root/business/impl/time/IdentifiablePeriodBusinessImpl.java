package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.time.DurationTypeBusiness;
import org.cyk.system.root.business.api.time.IdentifiablePeriodBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriod.Filter;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodDao;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.joda.time.DateTimeConstants;

public class IdentifiablePeriodBusinessImpl extends AbstractCollectionItemBusinessImpl<IdentifiablePeriod,IdentifiablePeriodDao,IdentifiablePeriodCollection> implements IdentifiablePeriodBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public IdentifiablePeriodBusinessImpl(IdentifiablePeriodDao dao) {
		super(dao); 
	}
	
	@Override
	public IdentifiablePeriod findFirstNotClosedOrInstanciateOneByIdentifiablePeriodCollection(IdentifiablePeriodCollection collection) {
		IdentifiablePeriod.Filter filter = new IdentifiablePeriod.Filter().addMaster(collection);
		filter.getGlobalIdentifier().getClosed().setValues(Boolean.FALSE);
		Collection<IdentifiablePeriod> identifiablePeriods = inject(IdentifiablePeriodDao.class).readByFilter(filter);
		IdentifiablePeriod identifiablePeriod = CollectionHelper.getInstance().getFirst(identifiablePeriods);
		if(identifiablePeriod == null && collection.getType()!=null && Boolean.TRUE.equals(collection.getType().getAutomaticallyCreateIdentifiablePeriodWhenNoneFound())){
			identifiablePeriod = instanciateOne().setCollection(collection);
			computeChanges(identifiablePeriod);
		}
		return identifiablePeriod;
	}
	
	@Override
	protected void beforeCrud(IdentifiablePeriod identifiablePeriod, Crud crud) {
		super.beforeCrud(identifiablePeriod, crud);
		if(Crud.CREATE.equals(crud)) {
			IdentifiablePeriod.Filter filter = new IdentifiablePeriod.Filter();
	    	filter.getGlobalIdentifier().getClosed().setValues(Boolean.FALSE);
	    	filter.addMaster(identifiablePeriod.getCollection());
	    	
			throw__(new ConditionHelper.Condition.Builder.Adapter.Default().setValueNameIdentifier("identifiablePeriodClosed")
					.setDomainNameIdentifier("identifiablePeriod").setConditionValue(dao.countByFilter(filter) > 0)
					.setMessageIdentifier("allmustbeCLOSED"));		
		}
	}
	
	@Override
	protected void computeChanges(IdentifiablePeriod identifiablePeriod, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(identifiablePeriod, loggingMessageBuilder);
		if(identifiablePeriod.getBirthDate() == null) {
			Long countAll = dao.countAll();
			if(countAll > 0) {
				//Long numberOfNotClosed = dao.countByClosed(Boolean.FALSE);				
				if(identifiablePeriod.getCollection() == null || identifiablePeriod.getCollection().getType()==null) {
					
				}else {
					
				}
				Collection<IdentifiablePeriod> recents = dao.readByFilter(new Filter(), new DataReadConfiguration().setFirstResultIndex(countAll-1).setMaximumResultCount(1l));
				if(CollectionHelper.getInstance().isEmpty(recents)) {
					identifiablePeriod.setBirthDate(TimeHelper.getInstance().getUniversalTimeCoordinated());
				}else {
					identifiablePeriod.setBirthDate(recents.iterator().next().getDeathDate());
				}
							
			}else {
				identifiablePeriod.setBirthDate(TimeHelper.getInstance().getUniversalTimeCoordinated());
			}
				
		}
		
		if(identifiablePeriod.getDeathDate() == null) {
			Long duration;
			if(identifiablePeriod.getCollection() == null || identifiablePeriod.getCollection().getType() == null)
				duration = new Long(DateTimeConstants.MILLIS_PER_DAY);
			else {
				if(identifiablePeriod.getCollection().getType().getPeriodDurationType() == null)
					duration = identifiablePeriod.getCollection().getType().getTimeDivisionType().getMeasure().getValue().longValue();
				else {
					Date expectedFromDate = null , expectedToDate = null;
					if(RootConstant.Code.TimeDivisionType.DAY.equals(identifiablePeriod.getCollection().getType().getTimeDivisionType().getCode())) {
						expectedFromDate = TimeHelper.getInstance().getEarliestOfTheDay(identifiablePeriod.getBirthDate());
						expectedToDate = TimeHelper.getInstance().getLatestOfTheDay(identifiablePeriod.getBirthDate());
					}
					
					if(expectedFromDate == null)
						expectedFromDate = TimeHelper.getInstance().getEarliestOfTheDay(identifiablePeriod.getBirthDate());
					if(expectedToDate == null)
						expectedToDate = TimeHelper.getInstance().getLatestOfTheDay(identifiablePeriod.getBirthDate());
					
					duration = inject(DurationTypeBusiness.class).computeNumberOfMillisecond(identifiablePeriod.getCollection().getType().getPeriodDurationType()
							, expectedFromDate, expectedToDate, identifiablePeriod.getBirthDate());
				}
			}
			identifiablePeriod.setDeathDate(new Date(identifiablePeriod.getBirthDate().getTime() + duration ));
		}
		
		if(identifiablePeriod.getClosed() == null) {
			identifiablePeriod.setClosed(Boolean.FALSE);
		}
	}
	
}