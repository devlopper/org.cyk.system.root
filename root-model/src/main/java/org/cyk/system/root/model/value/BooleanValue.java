package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.cyk.system.root.model.value.AbstractValue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor 
public class BooleanValue extends AbstractValue<Boolean> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	public BooleanValue(Boolean value) {
		super(value);
	}
		
}
