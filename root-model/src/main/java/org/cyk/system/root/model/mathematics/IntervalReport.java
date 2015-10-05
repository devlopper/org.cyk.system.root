package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter
public class IntervalReport extends AbstractGeneratable<IntervalReport> implements Serializable {

	private static final long serialVersionUID = -3815250939177148339L;

	private String code,name;
	
	@Override
	public void generate() {
		code = provider.randomWord(1, 3);
		name = provider.randomWord(5, 15);
	}

}
