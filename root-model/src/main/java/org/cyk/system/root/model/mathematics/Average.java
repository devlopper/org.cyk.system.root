package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Average implements Serializable{

	private static final long serialVersionUID = -4640228506073441626L;

	public static final BigDecimal _100 = new BigDecimal("100");
	
	private List<Weightable> weightables = new LinkedList<Weightable>();
	/*
	@Getter private boolean percentage;
	@Getter @Setter private String percentageSymbol = "%";
	@Getter @Setter private boolean appendPercentageSymbolToValueAsString = true;
	
	@Getter @Setter private int notPercentageScale=2,percentageScale=2;
	@Getter private int divideScale = notPercentageScale;
	@Getter @Setter private RoundingMode divideRoundMode = RoundingMode.DOWN;//truncates
	*/

	private BigDecimal dividend,divisor,value;
	
	/*
	@Getter private ValueTransformer 
			dividendValueTransformer = new ValueTransformer(),
			divisorValueTransformer = new ValueTransformer(),
			valueTransformer = null;//new ValueTransformer();
	*/
	public Average() {}
	/*
	public Average(BigDecimal dividend, BigDecimal divisor) {
		super();
		this.dividend = dividend;
		this.divisor = divisor;
		processDividendDivisor();
	}
	
	public Average(BigDecimal dividend, BigDecimal divisor,int divideScale,RoundingMode divideRoundMode) {
		super();
		this.dividend = dividend;
		this.divisor = divisor;
		this.divideRoundMode = divideRoundMode;
		this.divideScale = divideScale;
		processDividendDivisor();
	}


	*/
	/*-----------------------------------------------------------------------------*/
	
	public boolean add(Weightable weightable) {
		return weightables.add(weightable);
	}

	public boolean addAll(Collection<? extends Weightable> weightableCollection) {
		return weightables.addAll(weightableCollection);
	}

	public void clear() {
		weightables.clear();
	}
	
	/*-----------------------------------------------------------------------------*/
	/*
	public void setPercentage(boolean percentage) {
		this.percentage = percentage;
		this.divideScale = percentage?percentageScale:notPercentageScale;
	}
	
	public String getValueAsString(){
		if(percentage)
			return value.toPlainString() + (appendPercentageSymbolToValueAsString?percentageSymbol:"");
		return value.toPlainString();
	}
	*/
	/*-----------------------------------------------------------------------------*/
	@Override
	public String toString() {
		return dividend+"/"+divisor+" = "+value;
	}
	
}
