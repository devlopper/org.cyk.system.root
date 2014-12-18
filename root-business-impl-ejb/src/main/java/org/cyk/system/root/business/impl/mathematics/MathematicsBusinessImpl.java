package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import org.cyk.system.root.business.api.mathematics.MathematicsBusiness;
import org.cyk.system.root.model.mathematics.Average;
import org.cyk.system.root.model.mathematics.Weightable;

public class MathematicsBusinessImpl implements MathematicsBusiness,Serializable {

	private static final long serialVersionUID = 216333383963637261L;

	@Override
	public Average compute(Average average) {
		average.setDividend(BigDecimal.ZERO);
		average.setDivisor(BigDecimal.ZERO);
		average.setValue(BigDecimal.ZERO);
		
		for(Weightable weightable : average.getWeightables()){
			average.setDividend(average.getDividend().add(weightable.getValue().multiply(weightable.getWeight())));
			average.setDivisor(average.getDivisor().add(weightable.getWeight()));
		}
		
		if(average.getDivisor().equals(BigDecimal.ZERO)){
			average.setValue(null);
		}else{
			/*dividend = dividendValueTransformer.transform(dividend);
			divisor = divisorValueTransformer.transform(divisor);
			
			value = dividend.divide(divisor,divideScale,divideRoundMode);
			if(percentage)
				value = value.multiply(_100).setScale(divideScale);	
			
			if(valueTransformer!=null)
				value = valueTransformer.transform(value);*/
		}
		return average;
	}

}
