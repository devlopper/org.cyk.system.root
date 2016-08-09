package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.persistence.api.geography.AbstractContactDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractContactDaoImpl<CONTACT extends Contact> extends AbstractTypedDao<CONTACT> implements AbstractContactDao<CONTACT>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	protected String readByValue,countByValue,readByCollection,countByCollection;
	
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByCollection, _select().where(Contact.FIELD_COLLECTION));
    }
    
    @Override
    public Collection<CONTACT> readByCollection(ContactCollection collection) {
    	return namedQuery(countByCollection).parameter(Contact.FIELD_COLLECTION, collection).resultMany();
    }
    
    public Long countByCollection(ContactCollection collection) {
    	return countNamedQuery(countByCollection).parameter(Contact.FIELD_COLLECTION, collection).resultOne();
    }
 
}
 