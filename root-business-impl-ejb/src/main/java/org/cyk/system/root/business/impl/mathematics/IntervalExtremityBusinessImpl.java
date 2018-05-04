package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.mathematics.IntervalExtremityBusiness;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.InstanceHelper;

public class IntervalExtremityBusinessImpl implements IntervalExtremityBusiness , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void computeChanges(IntervalExtremity intervalExtremity) {
		if(intervalExtremity.getValueWithoutExcludedInformation() == null){
			if(intervalExtremity.getValue() == null)
				intervalExtremity.setValueWithoutExcludedInformationFromObject(Boolean.TRUE.equals(intervalExtremity.getIsLow()) ? Constant.NUMBER_LOWEST_NEGATIVE : Constant.NUMBER_HIGHEST_POSITIVE);
			else {
				if(Boolean.TRUE.equals(intervalExtremity.getExcluded())){
					BigDecimal step = BigDecimal.ONE.divide(new BigDecimal("1"+StringUtils.repeat("0", InstanceHelper.getInstance()
							.getIfNotNullElseDefault(intervalExtremity.getInterval().getNumberOfDecimalAfterDot(),new Byte("0")))));
					intervalExtremity.setValueWithoutExcludedInformationFromObject(intervalExtremity.getValue().add(Boolean.TRUE.equals(intervalExtremity.getIsLow()) 
							?  step : step.negate()));
				}else
					intervalExtremity.setValueWithoutExcludedInformationFromObject(intervalExtremity.getValue());
			}
				
		}
	}

}
