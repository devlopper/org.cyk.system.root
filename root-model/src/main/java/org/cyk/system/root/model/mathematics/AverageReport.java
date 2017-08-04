package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.generator.RandomDataProvider;

@Getter @Setter
public class AverageReport extends AbstractGeneratable<AverageReport> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dividend,divisor,value;
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		if(source!=null){
			dividend = format(((Average)source).getDividend());
			divisor = format(((Average)source).getDivisor());
			value = format(((Average)source).getValue());
		}
	}
	
	@Override
	public void generate() {
		dividend = String.valueOf(RandomDataProvider.getInstance().randomInt(1, 99));
		divisor = String.valueOf(RandomDataProvider.getInstance().randomInt(1, 99));
		value = String.valueOf(RandomDataProvider.getInstance().randomInt(1, 99));
	}

}
