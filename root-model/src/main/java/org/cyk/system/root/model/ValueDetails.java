package org.cyk.system.root.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ValueDetails extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	@Column(precision=10,scale=FLOAT_SCALE) @Input @InputNumber 
	private BigDecimal user;
	
	@Column(precision=10,scale=FLOAT_SCALE)
	private BigDecimal system;
	
	@Column(precision=10,scale=FLOAT_SCALE) 
	private BigDecimal gap;
	
	public ValueDetails(BigDecimal user) {
		super();
		this.user = user;
	}
	
	public void computeGap(){
		gap = system==null?null:(user==null?null:system.subtract(user));
	}
	
	@Override
	public String getUiString() {
		return "U="+user+" , S="+system+" , G="+gap;
	}

	@Override
	public String toString() {
		return "U="+user+" , S="+system+" , G="+gap;
	}
}
