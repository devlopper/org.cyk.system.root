package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.PhoneNumber;

@Getter @Setter
public class PhoneNumberDetails extends AbstractContactDetails<PhoneNumber> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	private FieldValue country,type,locationType;
	
	public PhoneNumberDetails(PhoneNumber phoneNumber) {
		super(phoneNumber);
	}

	@Override
	public void setMaster(PhoneNumber phoneNumber) {
		super.setMaster(phoneNumber);
		if(phoneNumber!=null){
			if(phoneNumber.getCountry()!=null)
				country = new FieldValue(phoneNumber.getCountry());
			if(phoneNumber.getType()!=null)
				type = new FieldValue(phoneNumber.getType());
			if(phoneNumber.getLocationType()!=null)
				locationType = new FieldValue(phoneNumber.getLocationType());
		}
	}
	
	public static final String FIELD_COUNTRY = "country";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_LOCATION_TYPE = "locationType";
}
