package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractActorDaoImpl<ACTOR extends AbstractActor> extends AbstractTypedDao<ACTOR> implements AbstractActorDao<ACTOR>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	
}
 