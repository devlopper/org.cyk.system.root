package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;

@Embeddable @Getter @Setter @NoArgsConstructor
public class IntervalExtremity extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -8646753247708396439L;

	@Column(precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE)
	private BigDecimal value;
	
	private Boolean excluded=Boolean.FALSE;
	//TODO add an attribute to keep final value
	@Transient private Boolean isLow;
	
	public IntervalExtremity(BigDecimal value,Boolean excluded) {
		super();
		this.value = value;
		this.excluded = excluded;
	}
	public IntervalExtremity(BigDecimal value) {
		this(value,Boolean.FALSE);
	}
	
	@Override
	public String getUiString() {
		return value==null?"INFINTE":value.toString();
	}
	
	@Override
	public String toString() {
		return value==null?"INFINTE":value.toString();
	}
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_EXCLUDED = "excluded";
	
	public static final String FORMAT = "%s%s";

	public static final String INFINITE = "INFINTE";
}
