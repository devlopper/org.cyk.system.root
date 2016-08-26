package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import org.cyk.utility.common.generator.AbstractGeneratable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonActorReport extends AbstractGeneratable<CommonActorReport> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected PersonReport person = new PersonReport();
	
	@Override
	public void generate() {
		person.generate();
	}

	
	
}
