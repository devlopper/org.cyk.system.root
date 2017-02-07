package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.MathematicsBusiness;
import org.cyk.system.root.business.api.mathematics.MathematicsBusiness.RankOptions.RankType;
import org.cyk.system.root.business.api.mathematics.Sortable;
import org.cyk.system.root.business.api.mathematics.WeightedValue;
import org.cyk.system.root.business.impl.AbstractBusinessServiceImpl;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.mathematics.Average;
import org.cyk.system.root.model.mathematics.Rank;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.formatter.NumberFormatter;

public class MathematicsBusinessImpl extends AbstractBusinessServiceImpl implements MathematicsBusiness,Serializable {

	private static final long serialVersionUID = 216333383963637261L;
	
	private static final NumberComputationListener NUMBER_COMPUTATION_LISTENER = new NumberComputationListener() {
 
		@Override
		public Integer scale(BigDecimal number) {
			return 2;
		}
		
		@Override
		public RoundingMode roundingMode(BigDecimal number) {
			return RoundingMode.DOWN;
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
	public Average average(Collection<WeightedValue> weightedValues,AverageComputationListener computationListener,Script script) {
		logTrace("Computing average WeightedValues={} computationListener prodived={} script provided={}", weightedValues.size(),computationListener!=null,script!=null);
		Average average = new Average();
		average.setDividend(BigDecimal.ZERO);
		average.setDivisor(BigDecimal.ZERO);
		average.setValue(null);
		
		for(WeightedValue weightedValue : weightedValues){
			if(weightedValue.getWeightApplied()==null || Boolean.TRUE.equals(weightedValue.getWeightApplied()))
				average.setDividend(average.getDividend().add(weightedValue.getValue()));
			else
				average.setDividend(average.getDividend().add(weightedValue.getValue().multiply(weightedValue.getWeight())));
			average.setDivisor(average.getDivisor().add(weightedValue.getWeight()));
		}
		
		if(script==null){	
			if(average.getDivisor().equals(BigDecimal.ZERO)){
				;
			}else{
				if(computationListener==null)
					computationListener = AVERAGE_COMPUTATION_LISTENER;
				average.setDividend(average.getDividend().setScale(computationListener.getDividendComputationListener().scale(null), 
						computationListener.getDividendComputationListener().roundingMode(null)));
				average.setDivisor(average.getDivisor().setScale(computationListener.getDivisorComputationListener().scale(null),
						computationListener.getDivisorComputationListener().roundingMode(null)));
				
				average.setValue(average.getDividend().divide(average.getDivisor(),computationListener.getValueComputationListener().scale(null),
						computationListener.getValueComputationListener().roundingMode(null)));
				
			}
		}else{
			script.getInputs().clear();
			script.getInputs().put(DIVIDEND, average.getDividend());
			script.getInputs().put(DIVISOR, average.getDivisor());
			scriptBusiness.evaluate(script);
			
			if(script.getOutputs().get(DIVIDEND)!=null)
				average.setDividend(new BigDecimal(script.getOutputs().get(DIVIDEND).toString()));
			if(script.getOutputs().get(DIVISOR)!=null)
				average.setDivisor(new BigDecimal(script.getOutputs().get(DIVISOR).toString()));
			if(script.getOutputs().get(AVERAGE)!=null)
				average.setValue(new BigDecimal(script.getOutputs().get(AVERAGE).toString()));
		}
		logTrace("Dividend={} Divisor={} Average={}", average.getDividend(),average.getDivisor(),average.getValue());
		return average;
	}
	
	@Override
	public <SORTABLE extends Sortable> void sort(List<SORTABLE> sortables,SortOptions<SORTABLE> options) {
		if(sortables==null || sortables.isEmpty())
			return;
		Collections.sort(sortables,options.getComparator());
		logTrace("{} element(s) sorted", sortables.size());
	}
	
	@Override
	public <SORTABLE extends Sortable> void rank(/*Class<SORTABLE> aClass,*/List<SORTABLE> sortables,RankOptions<SORTABLE> options) {
		logTrace("Ranking sort of {} element(s) Options={}", sortables.size(),options);
		if(sortables==null || sortables.isEmpty())
			return;
		//Collections.sort(sortables,ComparatorUtils.reversedComparator(comparator));
		sort(sortables, options.getSortOptions());
		RankType type = options.getType();
		if(type==null)
			type = RankType.SEQUENCE;
		int value = 1,i=0,j=0,sequenceOrder=0;	
		for(SORTABLE sortable : sortables){
			sortable.getRank().setSequenceOrder(sequenceOrder++);
			sortable.getRank().setValue(null);
			sortable.getRank().setExaequo(null);
			if(sortable.getValue()==null){
				
			}else{
				if(++j>1)
					if(RankType.EXAEQUO.equals(type))
						if(sortable.getValue().equals(sortables.get(i-1).getValue()))
							sortable.getRank().setExaequo(Boolean.TRUE);
						else 
							value = j;
					else if(RankType.SEQUENCE.equals(type))
						value = j;
				sortable.getRank().setValue(value);
			}
			i++;
		}
	}
	
	@Override
	public String format(Rank rank,NumberFormatter.String formatter) {
		formatter.setInput(rank.getValue());
		return formatter.execute(); 
	}
	
	@Override
	public String format(Rank rank) {
		NumberFormatter.String formatter = new NumberFormatter.String.Adapter.Default(null,null);
		formatter.setIsOrdinal(Boolean.TRUE);
		formatter.setLocale(inject(LanguageBusiness.class).findCurrentLocale());
		formatter.setIsAppendOrdinalSuffix(Boolean.TRUE);
		formatter.setIsAppendExaequo(Boolean.TRUE.equals(rank.getExaequo()));
		return format(rank,formatter);
	}
	
	@Override
	public <T> void sortByRank(final SortByRankArguments<T> arguments) {
		Collections.sort(arguments.getObjects(), new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return arguments.getRank(o1).getSequenceOrder().compareTo(arguments.getRank(o2).getSequenceOrder());
			}
		});
	}
	
	/**/
	
	public static class AverageComputationAdapter extends BeanAdapter implements AverageComputationListener,Serializable{

		private static final long serialVersionUID = 5433049041548830027L;

		@Override
		public NumberComputationListener getDividendComputationListener() {
			return null;
		}

		@Override
		public NumberComputationListener getDivisorComputationListener() {
			return null;
		}

		@Override
		public NumberComputationListener getValueComputationListener() {
			return null;
		}
		
	}

}
