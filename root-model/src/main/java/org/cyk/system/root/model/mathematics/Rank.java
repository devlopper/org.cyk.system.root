package org.cyk.system.root.model.mathematics;

import java.math.BigDecimal;

import lombok.Data;

@Data
public abstract class Rank implements Comparable<Rank>{

	private int val;
	private boolean exaequo;
	
	protected abstract boolean include();
	
	protected abstract BigDecimal valueUsedForComparison();
	
	protected Comparable<? extends Comparable<?>> __extraValueUsedForComparison__1__(){return null;};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compareTo(Rank o) {
		//DESC order
		if(o==null)
			return 0;
		if(o.valueUsedForComparison()==null && valueUsedForComparison()==null)
			return 0;
		if(o.valueUsedForComparison()==null)
			return 0;
		if(valueUsedForComparison()==null)
			return 1;
		int c1 = o.valueUsedForComparison().compareTo(valueUsedForComparison());
		if(c1!=0)
			return c1;
		Comparable cv1 = __extraValueUsedForComparison__1__();
		if(cv1==null)
			return c1;
		Comparable cv2 = o.__extraValueUsedForComparison__1__();
		return cv1.compareTo(cv2);//alphabetic ASC order
	}
	
	public String rankString() {
		return valueUsedForComparison()+" - "+val+" - "+exaequo;
	}
	
	@Override
	public String toString() {
		return rankString();
	}
	
}
