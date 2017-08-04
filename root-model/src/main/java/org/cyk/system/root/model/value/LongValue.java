package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor 
public class LongValue extends AbstractNumberValue<Long> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	public LongValue(Long user) {
		super(user);
	}

	@Override
	protected Long getZeroValue() {
		return 0l;
	}

	@Override
	protected Long __add__(Long value1, Long value2) {
		return value1+value2;
	}

	@Override
	protected Long __subtract__(Long value1, Long value2) {
		return value1-value2;
	}
	
	
	
}
