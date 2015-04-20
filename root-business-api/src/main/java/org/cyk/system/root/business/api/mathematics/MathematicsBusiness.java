package org.cyk.system.root.business.api.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.mathematics.Average;
import org.cyk.system.root.model.mathematics.Rank;

public interface MathematicsBusiness {

	String DIVIDEND = "dividend";
	String DIVISOR = "divisor";
	String AVERAGE = "average";
	
	Average average(Collection<WeightedValue> weightedValues,AverageComputationListener computationListener,Script script);
	
	<SORTABLE extends Sortable> void sort(List<SORTABLE> sortables,SortOptions<SORTABLE> options);
	
	<SORTABLE extends Sortable> void rank(/*Class<SORTABLE> aClass,*/List<SORTABLE> sortables,RankOptions<SORTABLE> options);
	
	String format(Rank rank);
	
	/**/
	
	interface NumberComputationListener{
		Integer scale(BigDecimal number);
		RoundingMode roundingMode(BigDecimal number);
	}
	
	interface AverageComputationListener{
		NumberComputationListener getDividendComputationListener();
		NumberComputationListener getDivisorComputationListener();
		NumberComputationListener getValueComputationListener();
	}
	
	@Getter @Setter
	public static class SortOptions<SORTABLE extends Sortable> implements Serializable {
		private static final long serialVersionUID = -6280959516917831703L;
		private Comparator<SORTABLE> comparator;
		private Script compareScript;
	}
	
	@Getter @Setter
	public static class RankOptions<SORTABLE extends Sortable> implements Serializable {
		private static final long serialVersionUID = -7252122787940322411L;
		public static enum RankType {SEQUENCE,EXAEQUO}
		private RankType type;
		private SortOptions<SORTABLE> sortOptions = new SortOptions<SORTABLE>();
	}
	
}
