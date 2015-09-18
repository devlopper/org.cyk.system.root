package org.cyk.system.root.business.api;

import java.util.List;

import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.security.Role;

public interface RootBusinessLayerListener {

	String generateActorRegistrationCode(AbstractActor actor,String previousCode);
	
	<ACTOR extends AbstractActor> AbstractActorBusiness<ACTOR> findActorBusiness(ACTOR actor);
	
	void populateCandidateRoles(List<Role> roles);
	
}
