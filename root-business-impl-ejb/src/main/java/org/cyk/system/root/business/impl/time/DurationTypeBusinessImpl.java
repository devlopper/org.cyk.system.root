package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.DurationTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.time.DurationType;
import org.cyk.system.root.persistence.api.time.DurationTypeDao;
import org.cyk.utility.common.helper.TimeHelper;

public class DurationTypeBusinessImpl extends AbstractEnumerationBusinessImpl<DurationType, DurationTypeDao> implements DurationTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public DurationTypeBusinessImpl(DurationTypeDao dao) {
		super(dao); 
	}
	
	@Override
	public Long computeNumberOfMillisecond(DurationType durationType, Date expectedFromDate, Date expectedToDate,Date currentFromDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Long computeNumberOfMillisecond(DurationType durationType, Date expectedFromDate, Date expectedToDate) {
		return computeNumberOfMillisecond(durationType, expectedFromDate, expectedToDate, TimeHelper.getInstance().getUniversalTimeCoordinated());
	}
	
	/**/
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<DurationType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(DurationType.class);
		}
	}
	
}
