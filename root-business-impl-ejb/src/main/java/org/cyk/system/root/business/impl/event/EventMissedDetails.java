package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class EventMissedDetails extends AbstractOutputDetails<EventMissed> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String eventParty,reason,numberOfMillisecond;
	
	public EventMissedDetails(EventMissed eventMissed) {
		super(eventMissed);
		eventParty = formatUsingBusiness(eventMissed.getEventParty());
		reason = formatUsingBusiness(eventMissed.getReason());
		numberOfMillisecond = formatNumber(eventMissed.getNumberOfMillisecond());
	}
	
	public static final String FIELD_EVENT_PARTY = "eventParty";
	public static final String FIELD_REASON = "reason";
	public static final String FIELD_NUMBER_OF_MILLISECOND = "numberOfMillisecond";
}