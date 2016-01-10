package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class EventParticipationDetails extends AbstractOutputDetails<EventParticipation> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String names,present;
	public EventParticipationDetails(EventParticipation eventParticipation) {
		super(eventParticipation);
		names = ((Person)eventParticipation.getParty()).getNames();
	}
}