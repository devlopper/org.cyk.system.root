package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.mathematics.Average;
import org.cyk.system.root.model.mathematics.Rankable;

public interface MathematicsBusiness {

	String DIVIDEND = "dividend";
	String DIVISOR = "divisor";
	String AVERAGE = "average";
	Average average(Average average,AverageComputationListener computationListener,Script script);
	
	enum RankType {SEQUENCE,EXAEQUO}
	<RANKABLE extends Rankable> void rank(Class<RANKABLE> aClass,RankType type,List<RANKABLE> rankables,Comparator<RANKABLE> comparator,Script compareScript);
	
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
	
}
