package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.ComparatorUtils;
import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.mathematics.MathematicsBusiness;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.mathematics.Average;
import org.cyk.system.root.model.mathematics.Rankable;
import org.cyk.system.root.model.mathematics.Weightable;

public class MathematicsBusinessImpl implements MathematicsBusiness,Serializable {

	private static final long serialVersionUID = 216333383963637261L;
	
	private static final NumberComputationListener NUMBER_COMPUTATION_LISTENER = new NumberComputationListener() {

		@Override
		public Integer scale(BigDecimal number) {
			return 2;
		}
		
		@Override
		public RoundingMode roundingMode(BigDecimal number) {
			return RoundingMode.HALF_DOWN;
		}
	};
	
	private static final AverageComputationListener AVERAGE_COMPUTATION_LISTENER = new AverageComputationListener() {
		
		@Override
		public NumberComputationListener getValueComputationListener() {
			return NUMBER_COMPUTATION_LISTENER;
		}
		
		@Override
		public NumberComputationListener getDivisorComputationListener() {
			return NUMBER_COMPUTATION_LISTENER;
		}
		
		@Override
		public NumberComputationListener getDividendComputationListener() {
			return NUMBER_COMPUTATION_LISTENER;
		}
	};
	
	@Inject private ScriptBusiness scriptBusiness;
	
	@Override
	public Average average(Average average,AverageComputationListener computationListener,Script script) {
		average.setDividend(BigDecimal.ZERO);
		average.setDivisor(BigDecimal.ZERO);
		average.setValue(null);
		
		for(Weightable weightable : average.getWeightables()){
			average.setDividend(average.getDividend().add(weightable.getValue() /*weightable.getValue().multiply(weightable.getWeight())*/));
			average.setDivisor(average.getDivisor().add(weightable.getWeight()));
		}
		
		if(script==null){	
			if(average.getDivisor().equals(BigDecimal.ZERO)){
				;
			}else{
				if(computationListener==null)
					computationListener = AVERAGE_COMPUTATION_LISTENER;
				average.setDividend(new BigDecimal(average.getDividend().doubleValue(), new MathContext(computationListener.getDividendComputationListener().scale(null), 
						computationListener.getDividendComputationListener().roundingMode(null))));
				average.setDivisor(new BigDecimal(average.getDivisor().doubleValue(), new MathContext(computationListener.getDivisorComputationListener().scale(null),
						computationListener.getDivisorComputationListener().roundingMode(null))));
				
				average.setValue(average.getDividend().divide(average.getDivisor(),computationListener.getValueComputationListener().scale(null),
						computationListener.getValueComputationListener().roundingMode(null)));
				
			}
		}else{
			Map<String,Object> inputs = new HashMap<>();
			inputs.put(DIVIDEND, average.getDividend());
			inputs.put(DIVISOR, average.getDivisor());
			Map<String, Object> results = scriptBusiness.evaluate(script, inputs);
			
			if(results.get(DIVIDEND)!=null)
				average.setDividend(new BigDecimal(results.get(DIVIDEND).toString()));
			if(results.get(DIVISOR)!=null)
				average.setDivisor(new BigDecimal(results.get(DIVISOR).toString()));
			if(results.get(AVERAGE)!=null)
				average.setValue(new BigDecimal(results.get(AVERAGE).toString()));
		}
		
		return average;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <RANKABLE extends Rankable> void rank(Class<RANKABLE> aClass,RankType type,List<RANKABLE> rankables,Comparator<RANKABLE> comparator,Script compareScript) {
		if(rankables==null)
			return;
		Collections.sort(rankables,ComparatorUtils.reversedComparator(comparator));
		if(type==null)
			type = RankType.SEQUENCE;
		int value = 1,i=0,j=0;	
		for(RANKABLE rankable : rankables){
			rankable.getRank().setValue(null);
			rankable.getRank().setExaequo(null);
			if(rankable.getValue()==null){
				
			}else{
				if(++j>1)
					if(RankType.EXAEQUO.equals(type))
						if(rankable.getValue().equals(rankables.get(i-1).getValue()))
							rankable.getRank().setExaequo(Boolean.TRUE);
						else 
							value = j;
					else if(RankType.SEQUENCE.equals(type))
						value = j;
				rankable.getRank().setValue(value);
			}
			i++;
		}
	}

}
