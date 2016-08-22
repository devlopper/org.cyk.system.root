package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractActorDetails<ACTOR extends AbstractIdentifiable> extends AbstractPersonDetails<ACTOR> implements Serializable {

	private static final long serialVersionUID = 1165482775425753790L;

	@Input @InputText private String registrationDate;
	
	public AbstractActorDetails(ACTOR actor) {
		super(actor);
		if(actor.getBirthDate()!=null)
			registrationDate = inject(TimeBusiness.class).formatDate(actor.getBirthDate());
	}

	/**/
	
	@Override
	protected Person getPerson() {
		return getActor().getPerson();
	}
	
	protected abstract AbstractActor getActor();
	
	/**/
	
	public static final String FIELD_REGISTRATION_DATE = "registrationDate";
	
	/**/
	
	public static class AbstractDefault<ACTOR extends AbstractActor> extends AbstractActorDetails<ACTOR> implements Serializable {

		private static final long serialVersionUID = -5041861481576596267L;

		public AbstractDefault(ACTOR actor) {
			super(actor);
		}

		@Override
		protected ACTOR getActor() {
			return master;
		}
		
		/**/
		
		public static class Default extends AbstractDefault<AbstractActor> implements Serializable {

			private static final long serialVersionUID = -5041861481576596267L;

			public Default(AbstractActor actor) {
				super(actor);
			}

		}
		
	}
	
	

}
