package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.geography.Location;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LocationDetails extends AbstractOutputDetails<Location> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;
	
	@Input @InputText private FieldValue type,locality;
	
	public LocationDetails(Location location) {
		super(location);		
	}
	
	@Override
	public void setMaster(Location location) {
		super.setMaster(location);
		if(location!=null){
			if(location.getType()!=null)
				type = new FieldValue(location.getType());
			if(location.getLocality()!=null)
				locality = new FieldValue(location.getLocality());
		}
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_LOCALITY = "locality";

}
