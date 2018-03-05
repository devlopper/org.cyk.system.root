package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.time.IdentifiablePeriodBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
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
	protected void beforeCrud(IdentifiablePeriod identifiablePeriod, Crud crud) {
		super.beforeCrud(identifiablePeriod, crud);
		if(Crud.CREATE.equals(crud)) {
			throw__(new ConditionHelper.Condition.Builder.Adapter.Default().setValueNameIdentifier("identifiablePeriodClosed")
					.setDomainNameIdentifier("identifiablePeriod").setConditionValue(dao.countByClosed(Boolean.FALSE) > 0)
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
			else
				duration = identifiablePeriod.getCollection().getType().getTimeDivisionType().getMeasure().getValue().longValue();
			identifiablePeriod.setDeathDate(new Date(identifiablePeriod.getBirthDate().getTime() + duration ));
		}
		
		if(identifiablePeriod.getClosed() == null) {
			identifiablePeriod.setClosed(Boolean.FALSE);
		}
	}
	
}