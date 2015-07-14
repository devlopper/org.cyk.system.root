package org.cyk.system.root.business.api.generator;

import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;

@Deprecated
public interface RootValueGenerator {

	String partyCode(Party aParty);
	
	String actorRegistrationCode(AbstractActor actor);
	
}
