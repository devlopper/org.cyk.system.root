package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;

@Embeddable @Getter @Setter @NoArgsConstructor
public class IntervalExtremity extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -8646753247708396439L;

	@Column(precision=30,scale=FLOAT_SCALE)
	private BigDecimal value;
	
	private Boolean excluded=Boolean.FALSE;
	
	public IntervalExtremity(BigDecimal value) {
		super();
		this.value = value;
	}
	
	@Override
	public String getUiString() {
		return value==null?"INFINTE":value.toString();
	}
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_EXCLUDED = "excluded";

}