package org.cyk.system.root.business.api.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.system.root.model.mathematics.Average;
import org.cyk.system.root.model.mathematics.Rank;
import org.cyk.utility.common.formatter.NumberFormatter;

public interface MathematicsBusiness {

	String DIVIDEND = "dividend";
	String DIVISOR = "divisor";
	String AVERAGE = "average";
	
	Average average(Collection<WeightedValue> weightedValues,AverageComputationListener computationListener,Script script);
	
	<SORTABLE extends Sortable> void sort(List<SORTABLE> sortables,SortOptions<SORTABLE> options);
	
	<SORTABLE extends Sortable> void rank(/*Class<SORTABLE> aClass,*/List<SORTABLE> sortables,RankOptions<SORTABLE> options);
	
	<T> void sortByRank(SortByRankArguments<T> arguments);
	
	String format(Rank rank,NumberFormatter.String formatter);
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
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_FIELD_NAMES_STYLE);
		}
	}
	
	@Getter @Setter
	public static class RankOptions<SORTABLE extends Sortable> implements Serializable {
		private static final long serialVersionUID = -7252122787940322411L;
		public static enum RankType {SEQUENCE,EXAEQUO}
		private RankType type;
		private SortOptions<SORTABLE> sortOptions = new SortOptions<SORTABLE>();
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_FIELD_NAMES_STYLE);
		}
	}
	
	public interface SortByRankArguments<T>{
		List<T> getObjects();
		Rank getRank(T object);
	}
	
}
