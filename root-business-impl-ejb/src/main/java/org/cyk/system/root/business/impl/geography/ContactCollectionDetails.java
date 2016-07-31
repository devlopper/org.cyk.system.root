package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class ContactCollectionDetails extends AbstractOutputDetails<ContactCollection> implements Serializable {
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private String phoneNumbers,electronicMails,locations,postalBoxes;
	
	public ContactCollectionDetails(ContactCollection contactCollection) {
		super(contactCollection);
		if(contactCollection==null){
			
		}else{
			phoneNumbers = StringUtils.join(contactCollection.getPhoneNumbers(),Constant.CHARACTER_COMA);
			electronicMails = StringUtils.join(contactCollection.getElectronicMails(),Constant.CHARACTER_COMA);
			locations = StringUtils.join(contactCollection.getLocations(),Constant.CHARACTER_COMA);
			postalBoxes = StringUtils.join(contactCollection.getPostalBoxs(),Constant.CHARACTER_COMA);
		}
		
	}
	
	/**/
	public static final String FIELD_PHONE_NUMBERS = "phoneNumbers";
	public static final String FIELD_ELECTRONIC_MAILS = "electronicMails";
	public static final String FIELD_LOCATIONS = "locations";
	public static final String FIELD_POSTAL_BOXES = "postalBoxes";
	
}