package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.CommonUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity  @NoArgsConstructor
public class IntervalCollection extends AbstractCollection<Interval> implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;

	@Column(precision=IntervalExtremity.COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE) private BigDecimal lowestValue;
	@Column(precision=IntervalExtremity.COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE) private BigDecimal highestValue;
	@Column(nullable=false) private Byte numberOfDecimalAfterDot = new Byte("2");
	
	public IntervalCollection(String code) {
		super(code, code,null,null);
	}

	public Interval addItem(String code, String name,String low,String high) {
		Interval interval = super.addItem(code,name);
		interval.getLow().setValue(CommonUtils.getInstance().getBigDecimal(low));
		interval.getHigh().setValue(CommonUtils.getInstance().getBigDecimal(high));
		return interval;
	}
	public Interval addItem(String name,String low,String high) {
		return addItem(null, name, low, high);
	}
	
	
	
}