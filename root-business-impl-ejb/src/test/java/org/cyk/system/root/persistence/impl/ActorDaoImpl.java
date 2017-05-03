package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

import org.cyk.system.root.model.Actor;
import org.cyk.system.root.persistence.api.ActorDao;
import org.cyk.system.root.persistence.impl.party.person.AbstractActorDaoImpl;

public class ActorDaoImpl extends AbstractActorDaoImpl<Actor,Actor.SearchCriteria> implements ActorDao,Serializable {

	private static final long serialVersionUID = -1712788156426144935L;

}
