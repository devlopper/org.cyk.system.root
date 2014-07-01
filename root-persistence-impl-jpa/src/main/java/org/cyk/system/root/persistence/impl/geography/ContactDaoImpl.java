package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class ContactDaoImpl extends AbstractTypedDao<Contact> implements ContactDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByClass;
	
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByClass, "SELECT contact FROM Contact contact WHERE TYPE(contact) = :aClass");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Contact> Collection<T> readAll(Class<T> aClass) {
        return (Collection<T>) namedQuery(readByClass).parameter("aClass", aClass).resultMany();
    }
	
   
 
}
 