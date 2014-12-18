package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class RankBuilder<T extends Rank> implements Serializable{

	private static final long serialVersionUID = -1403592214430520250L;
	public enum Type {SEQUENCE,EXAEQUO}
	
	public static Type TYPE = Type.EXAEQUO;
	public static final BigDecimal _1 = new BigDecimal("1");
	
	private List<T> elements = new LinkedList<T>();
	@Getter @Setter private Type type;
	@Getter private Average average = new Average();
	//@Getter private ValueTransformer averageTransformer = new ValueTransformer();
	
	public List<T> compute(){
		if(elements.isEmpty())
			return elements;
		Collections.sort(elements);
		__compute__();
		return elements;
	}
	
	public void __compute__(){
		Type type = this.type;
		if(type==null)
			type = TYPE;
		int rankValue = 1,i=0,j=0;
	
		for(T rank : elements){
			if(rank.include()){
				if(++j>1)
					if(Type.EXAEQUO.equals(type))
						if(rank.valueUsedForComparison().equals(elements.get(i-1).valueUsedForComparison()))
							rank.setExaequo(true);
						else 
							rankValue = j;
					else if(Type.SEQUENCE.equals(type))
						rankValue = j;
				rank.setVal(rankValue);
				average.add(new WeightableBigDecimal(rank.valueUsedForComparison()));
			}
			i++;
		}
		//average.compute();
	}

	public boolean add(T element) {
		return elements.add(element);
	}

	public boolean addAll(Collection<T> elements) {
		return elements.addAll(elements);
	}

	public void clear() {
		elements.clear();
	}
	
}

class WeightableBigDecimal implements Weightable,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4556030027975906601L;
	private BigDecimal bigDecimal;
	
	WeightableBigDecimal(BigDecimal bigDecimal) {
		super();
		this.bigDecimal = bigDecimal;
	}

	public BigDecimal getValue() {
		return bigDecimal;
	}

	public BigDecimal getWeight() {
		return RankBuilder._1;
	}

	public boolean isValueWeighted() {
		return false;
	}
	
}
