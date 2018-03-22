package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.DurationTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.RootConstant;
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
	public Long computeNumberOfMillisecond(String durationTypeCode, Date expectedFromDate, Date expectedToDate,Date currentFromDate) {
		TimeHelper.DurationType vDurationType = null;
		if(RootConstant.Code.DurationType.FULL.equals(durationTypeCode))
			vDurationType = TimeHelper.DurationType.FULL;
		else if(RootConstant.Code.DurationType.PARTIAL.equals(durationTypeCode))
			vDurationType = TimeHelper.DurationType.PARTIAL;
		return TimeHelper.getInstance().getNumberOfMillisecond(vDurationType, expectedFromDate, expectedToDate,currentFromDate);
	}
	
	@Override
	public Long computeNumberOfMillisecond(String durationTypeCode, Date expectedFromDate, Date expectedToDate) {
		return computeNumberOfMillisecond(durationTypeCode, expectedFromDate, expectedToDate, TimeHelper.getInstance().getUniversalTimeCoordinated());
	}
	
	@Override
	public Long computeNumberOfMillisecond(DurationType durationType, Date expectedFromDate, Date expectedToDate,Date currentFromDate) {
		return computeNumberOfMillisecond(durationType.getCode(), expectedFromDate, expectedToDate, currentFromDate);
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
