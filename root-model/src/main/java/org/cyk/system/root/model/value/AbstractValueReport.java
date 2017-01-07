package org.cyk.system.root.model.value;

import java.io.Serializable;

import org.cyk.utility.common.generator.AbstractGeneratable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractValueReport extends AbstractGeneratable<AbstractValueReport> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String preferredProperty,user,system;
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		preferredProperty = format(((AbstractValue<?>)source).getPreferredProperty());
		user = format(((AbstractValue<?>)source).getUser());
		system = format(((AbstractValue<?>)source).getSystem());
	}
	
	@Override
	public void generate() {
		preferredProperty = "12/10/2013";
		user = "23/11/2014";
		system = "23/11/2014";
	}

}
