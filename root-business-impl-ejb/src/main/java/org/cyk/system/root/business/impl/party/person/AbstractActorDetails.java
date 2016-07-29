package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;

public abstract class AbstractActorDetails<ACTOR extends AbstractIdentifiable> extends AbstractPersonDetails<ACTOR> implements Serializable {

	private static final long serialVersionUID = 1165482775425753790L;

	public AbstractActorDetails(ACTOR actor) {
		super(actor);
		
	}

	/**/
	
	protected abstract AbstractActor getActor();
	
	/**/

}
