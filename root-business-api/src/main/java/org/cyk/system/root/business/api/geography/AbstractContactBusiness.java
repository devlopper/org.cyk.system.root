package org.cyk.system.root.business.api.geography;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;

public interface AbstractContactBusiness<CONTACT extends Contact> extends TypedBusiness<CONTACT> {

	Collection<CONTACT> readByValue(String value);
    Long countByValue(String value);
    
    Collection<CONTACT> findByCollection(ContactCollection collection);
    Long countByCollection(ContactCollection collection);
    
}
