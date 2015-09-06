package org.cyk.system.root.persistence.api.geography;

import java.util.Collection;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractContactDao<CONTACT extends Contact> extends TypedDao<CONTACT> {

    Collection<CONTACT> readByValue(String value);
    Long countByValue(String value);
}
