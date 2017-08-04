package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class EventPartyDetails extends AbstractOutputDetails<EventParty> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String event,party;
	
	public EventPartyDetails(EventParty eventParty) {
		super(eventParty);
		event = formatUsingBusiness(eventParty.getEvent());
		party = formatUsingBusiness(eventParty.getParty());
	}
	
	public static final String FIELD_EVENT = "event";
	public static final String FIELD_PARTY = "party";
}