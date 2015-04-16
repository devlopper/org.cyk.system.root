package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;

@Getter @Setter @Embeddable @NoArgsConstructor @AllArgsConstructor
public class Average extends AbstractModelElement implements Serializable{

	private static final long serialVersionUID = -4640228506073441626L;

	@Column(precision=20,scale=FLOAT_SCALE) private BigDecimal dividend;
	@Column(precision=20,scale=FLOAT_SCALE) private BigDecimal divisor;
	@Column(precision=20,scale=FLOAT_SCALE) private BigDecimal value;
	
	@Override
	public String toString() {
		return dividend+"/"+divisor+" = "+value;
	}

	@Override
	public String getUiString() {
		return value.toString();
	}
	
}
