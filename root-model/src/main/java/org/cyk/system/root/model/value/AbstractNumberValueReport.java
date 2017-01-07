package org.cyk.system.root.model.value;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractNumberValueReport extends AbstractValueReport implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String gap;
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		gap = format(((AbstractNumberValue<?>)source).getGap());
	}
	
	@Override
	public void generate() {
		gap = "THISmYGap";
	}

}
