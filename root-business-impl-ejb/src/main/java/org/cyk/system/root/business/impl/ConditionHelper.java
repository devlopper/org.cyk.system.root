package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.utility.common.cdi.AbstractBean;

public class ConditionHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static Collection<org.cyk.utility.common.helper.ConditionHelper.Condition.Builder> getBuildersDoesNotBelongsTo(Object instance,Interval interval,String...fieldNames){
		return org.cyk.utility.common.helper.ConditionHelper.Condition.getBuildersDoesNotBelongsTo(instance, interval.getLow().getValueWithoutExcludedInformation()
				, interval.getHigh().getValueWithoutExcludedInformation(), fieldNames);
	}
	
}
