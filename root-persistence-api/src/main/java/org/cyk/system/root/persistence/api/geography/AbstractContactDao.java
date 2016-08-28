package org.cyk.system.root.persistence.api.geography;

import java.util.Collection;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public interface AbstractContactDao<CONTACT extends Contact> extends AbstractCollectionItemDao<Contact,ContactCollection> {

    Collection<CONTACT> readByValue(String value);
    Long countByValue(String value);
    
   
}
