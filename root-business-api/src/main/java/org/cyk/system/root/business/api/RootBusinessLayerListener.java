package org.cyk.system.root.business.api;

import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.model.party.person.AbstractActor;

public interface RootBusinessLayerListener {

	String generateActorRegistrationCode(AbstractActor actor,String previousCode);
	
	<ACTOR extends AbstractActor> AbstractActorBusiness<ACTOR> findActorBusiness(ACTOR actor);
	
}
