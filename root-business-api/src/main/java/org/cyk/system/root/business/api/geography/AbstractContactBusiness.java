package org.cyk.system.root.business.api.geography;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;

public interface AbstractContactBusiness<CONTACT extends Contact> extends AbstractCollectionItemBusiness<Contact, ContactCollection> {

	Collection<CONTACT> readByValue(String value);
    Long countByValue(String value);
    
}
