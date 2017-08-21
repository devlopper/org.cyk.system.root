package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.time.TimeDivisionTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.persistence.api.time.TimeDivisionTypeDao;

public class TimeDivisionTypeBusinessImpl extends AbstractEnumerationBusinessImpl<TimeDivisionType, TimeDivisionTypeDao> implements TimeDivisionTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public TimeDivisionTypeBusinessImpl(TimeDivisionTypeDao dao) {
		super(dao); 
	}
	
	@Override
	protected Object[] getPropertyValueTokens(TimeDivisionType timeDivisionType, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, name)){
			return new Object[]{timeDivisionType.getMeasure()};
		}
		return super.getPropertyValueTokens(timeDivisionType, name);
	}

	@Override
	public TimeDivisionType instanciateOne(String[] values) {
		TimeDivisionType timeDivisionType = super.instanciateOne(values);
		Integer index = 10;
		timeDivisionType.setMeasure(read(Measure.class, values[index++]));
		return timeDivisionType;
	}
	
	/**/
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<TimeDivisionType> implements Serializable {

		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(TimeDivisionType.class);
			addParameterArrayElementStringIndexInstance(10,TimeDivisionType.FIELD_MEASURE);
		}
	}
	
}
