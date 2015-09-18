package org.cyk.system.root.business.api;

import java.util.List;

import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.security.Role;
import org.cyk.utility.common.cdi.AbstractBean;

public class RootBusinessLayerAdapter extends AbstractBean implements RootBusinessLayerListener {
	
	private static final long serialVersionUID = -7771053357545118564L;

	@Override
	public String generateActorRegistrationCode(AbstractActor actor,String previousCode) {
		return null;
	}

	@Override
	public <ACTOR extends AbstractActor> AbstractActorBusiness<ACTOR> findActorBusiness(ACTOR actor) {
		return null;
	}

	@Override
	public void populateCandidateRoles(List<Role> roles) {
		
	}
}
