package org.cyk.system.root.business.api;

import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;

public interface RootValueGenerator {

	String partyCode(Party aParty);
	
	String actorRegistrationCode(AbstractActor actor);
	
}
