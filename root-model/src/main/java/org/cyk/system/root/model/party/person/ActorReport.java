package org.cyk.system.root.model.party.person;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter
public class ActorReport extends AbstractGeneratable<ActorReport> implements Serializable {

	private static final long serialVersionUID = -7349146237275151269L;

	protected PersonReport person = new PersonReport();
	
	protected String registrationCode,registrationDate;

	@Override
	public void generate() {
		person.generate();
		registrationCode = RandomStringUtils.randomNumeric(8);
		registrationDate = new Date().toString();
	}
	
}
