package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import org.cyk.utility.common.generator.AbstractGeneratable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SortReport extends AbstractGeneratable<SortReport> implements Serializable {

	private static final long serialVersionUID = 1L;

	private AverageReport average = new AverageReport();
	private RankReport rank = new RankReport();
	private IntervalReport averageAppreciatedInterval = new IntervalReport();
	private IntervalReport averagePromotedInterval = new IntervalReport();
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		if(source!=null){
			average.setSource(((Sort)source).getAverage());
			rank.setSource(((Sort)source).getRank());
			averageAppreciatedInterval.setSource(((Sort)source).getAverageAppreciatedInterval());
			averagePromotedInterval.setSource(((Sort)source).getAveragePromotedInterval());
		}
	}
	
	@Override
	public void generate() {
		average.generate();
		rank.generate();
	}

}
