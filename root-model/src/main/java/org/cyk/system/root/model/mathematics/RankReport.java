package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.formatter.NumberFormatter;
import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.generator.RandomDataProvider;

@Getter @Setter
public class RankReport extends AbstractGeneratable<RankReport> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sequenceOrder,exaequo,value,valueExaequo;
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		if(source!=null){
			sequenceOrder = format(((Rank)source).getSequenceOrder());
			exaequo = format(((Rank)source).getExaequo());
			if(((Rank)source).getValue()==null){
				value = NULL_VALUE.toString();
				valueExaequo = NULL_VALUE.toString();
			}else{
				NumberFormatter.String numberFormatter = new NumberFormatter.String.Adapter.Default(((Rank)source).getValue(),null);
				numberFormatter.setIsAppendOrdinalSuffix(Boolean.TRUE);
				numberFormatter.setIsOrdinal(Boolean.TRUE);
				numberFormatter.setLocale(AbstractGeneratable.Listener.Adapter.Default.LOCALE);
								
				value = numberFormatter.execute();
				
				numberFormatter.setIsAppendExaequo(((Rank)source).getExaequo());
				valueExaequo = numberFormatter.execute();
			}
		}
	}
	
	@Override
	public void generate() {
		sequenceOrder = String.valueOf(RandomDataProvider.getInstance().randomInt(1, 99));
		exaequo = String.valueOf(RandomDataProvider.getInstance().randomBoolean());
		value = String.valueOf(RandomDataProvider.getInstance().randomInt(1, 99));
		valueExaequo = value + exaequo;
	}

}
