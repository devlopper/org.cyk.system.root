package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.ActorBusiness;
import org.cyk.system.root.business.impl.party.person.AbstractActorBusinessImpl;
import org.cyk.system.root.model.Actor;
import org.cyk.system.root.persistence.api.ActorDao;

@Stateless
public class ActorBusinessImpl extends AbstractActorBusinessImpl<Actor, ActorDao,Actor.SearchCriteria> implements ActorBusiness,Serializable {

	private static final long serialVersionUID = -7830673760640348717L;

	@Inject
	public ActorBusinessImpl(ActorDao dao) {
		super(dao);
	}
	
}
