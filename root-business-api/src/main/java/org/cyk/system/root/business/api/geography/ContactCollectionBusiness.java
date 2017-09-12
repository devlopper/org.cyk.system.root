package org.cyk.system.root.business.api.geography;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;

public interface ContactCollectionBusiness extends AbstractCollectionBusiness<ContactCollection,Contact> {

	ContactCollection instanciateOne(String[] phoneNumbers,String[] electronicMail,String[] postalBoxes,String[] websites);
	
	void setElectronicMail(ContactCollection collection,String address);
	String getElectronicMailAddress(ContactCollection collection);
}
