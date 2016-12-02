package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor 
public class BooleanValue extends AbstractValue<Boolean> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	public BooleanValue(Boolean user) {
		super(user);
	}
	
	public BooleanValue set(Boolean value){
		return (BooleanValue) set(value,Boolean.TRUE);
	}
	
	@Override
	public void computeGap(){
		
	}
	
}
