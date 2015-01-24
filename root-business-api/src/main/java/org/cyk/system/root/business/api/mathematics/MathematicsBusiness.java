package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.mathematics.Average;
import org.cyk.system.root.model.mathematics.Rank;

public interface MathematicsBusiness {

	String DIVIDEND = "dividend";
	String DIVISOR = "divisor";
	String AVERAGE = "average";
	
	Average average(Collection<WeightedValue> weightedValues,AverageComputationListener computationListener,Script script);
	
	<RANKABLE extends Rankable> void rank(Class<RANKABLE> aClass,RankType type,List<RANKABLE> rankables,Comparator<RANKABLE> comparator,Script compareScript);
	
	String format(Rank rank);
	
	/**/
	
	enum RankType {SEQUENCE,EXAEQUO}
	
	interface NumberComputationListener{
		Integer scale(BigDecimal number);
		RoundingMode roundingMode(BigDecimal number);
	}
	
	interface AverageComputationListener{
		NumberComputationListener getDividendComputationListener();
		NumberComputationListener getDivisorComputationListener();
		NumberComputationListener getValueComputationListener();
	}
	
}
