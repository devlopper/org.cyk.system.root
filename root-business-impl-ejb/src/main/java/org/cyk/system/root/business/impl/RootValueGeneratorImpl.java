package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.generator.RootValueGenerator;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;

@Deprecated
public class RootValueGeneratorImpl implements RootValueGenerator,Serializable {

	private static final long serialVersionUID = -4852641723010830142L;

	//@Inject private ApplicationBusiness applicationBusiness;
	
	@Override
	public String partyCode(Party aParty) {
		return RandomStringUtils.randomAlphabetic(8);
	}

	@Override
	public String actorRegistrationCode(AbstractActor anActor) {
		return RandomStringUtils.randomAlphabetic(8);
	}
	
	/**/
	
	

}
