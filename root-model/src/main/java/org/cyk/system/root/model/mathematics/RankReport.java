package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.generator.RandomDataProvider;

@Getter @Setter
public class RankReport extends AbstractGeneratable<RankReport> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sequenceOrder,exaequo,value;
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		if(source!=null){
			sequenceOrder = format(((Rank)source).getSequenceOrder());
			exaequo = format(((Rank)source).getExaequo());
			value = format(((Rank)source).getValue());
		}
	}
	
	@Override
	public void generate() {
		sequenceOrder = String.valueOf(RandomDataProvider.getInstance().randomInt(1, 99));
		exaequo = String.valueOf(RandomDataProvider.getInstance().randomBoolean());
		value = String.valueOf(RandomDataProvider.getInstance().randomInt(1, 99));
	}

}
