package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;

@Getter @Setter @Entity  @NoArgsConstructor
public class IntervalCollection extends AbstractCollection<Interval> implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;

	@Transient private BigDecimal lowestValue,highestValue;
	
	public IntervalCollection(String code) {
		super(code, code,null,null);
	}

	public Interval addItem(String code, String name,String low,String high) {
		Interval interval = super.addItem(code, name);
		interval.getLow().setValue(new BigDecimal(low));
		interval.getHigh().setValue(new BigDecimal(high));
		return interval;
	}
	
	
	
}