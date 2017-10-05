package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.geography.LocationType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LocationTypeDetails extends AbstractOutputDetails<LocationType> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;
	
	public LocationTypeDetails(LocationType phoneNumberType) {
		super(phoneNumberType);		
	}

	
}
