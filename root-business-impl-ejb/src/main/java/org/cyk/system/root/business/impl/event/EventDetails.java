package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.time.PeriodDetails;
import org.cyk.system.root.model.event.Event;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EventDetails extends AbstractOutputDetails<Event> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@IncludeInputs(layout=Layout.VERTICAL) private PeriodDetails period;
	@IncludeInputs(layout=Layout.VERTICAL) private FieldValue contactCollection;
	
	public EventDetails(Event event) {
		super(event);
		period = new PeriodDetails(event.getExistencePeriod());
		//contactCollection = new ContactCollectionDetails(event.getContactCollection());
		
	}
	
	/**/
	
	public static final String FIELD_PERIOD = "period";
	public static final String FIELD_CONTACT_COLLECTION = "contactCollection";
}