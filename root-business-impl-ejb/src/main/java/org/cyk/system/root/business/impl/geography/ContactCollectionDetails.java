package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class ContactCollectionDetails extends AbstractOutputDetails<ContactCollection> implements Serializable {
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private String phoneNumbers,electronicMailAddresses,locations,postalBoxes;
	
	public ContactCollectionDetails(ContactCollection contactCollection) {
		super(contactCollection);
		if(contactCollection==null){
			
		}else{
			phoneNumbers = StringUtils.join(contactCollection.getItems().filter(PhoneNumber.class),Constant.CHARACTER_COMA);
			electronicMailAddresses = StringUtils.join(contactCollection.getItems().filter(ElectronicMailAddress.class),Constant.CHARACTER_COMA);
			locations = StringUtils.join(contactCollection.getItems().filter(Location.class),Constant.CHARACTER_COMA);
			postalBoxes = StringUtils.join(contactCollection.getItems().filter(PostalBox.class),Constant.CHARACTER_COMA);
		}
		
	}
	
	/**/
	public static final String FIELD_PHONE_NUMBERS = "phoneNumbers";
	public static final String FIELD_ELECTRONIC_MAIL_ADDRESSES = "electronicMailAddresses";
	public static final String FIELD_LOCATIONS = "locations";
	public static final String FIELD_POSTAL_BOXES = "postalBoxes";
	
}