package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractModelElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Embeddable @Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class IntervalExtremity extends AbstractModelElement implements Serializable {
	private static final long serialVersionUID = -8646753247708396439L;

	@Column(precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE) private BigDecimal value;
	private Boolean excluded;
	@Column(precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE) @NotNull private BigDecimal valueWithoutExcludedInformation;
	private @NotNull Boolean isLow;
	
	@Transient private Interval interval;
	
	public IntervalExtremity setValueFromObject(Object object){
		this.value = getNumberFromObject(BigDecimal.class, object);
		return this;
	}
	
	public IntervalExtremity setValueWithoutExcludedInformationFromObject(Object object){
		this.valueWithoutExcludedInformation = getNumberFromObject(BigDecimal.class, object);
		return this;
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
	public static final String FIELD_VALUE_WITHOUT_EXCLUDED_INFORMATION = "valueWithoutExcludedInformation";
	public static final String FIELD_IS_LOW = "isLow";
	
	public static final String FORMAT = "%s%s";

	public static final String INFINITE = "INFINTE";
}
