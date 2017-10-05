package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.persistence.api.geography.AbstractContactDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;

public abstract class AbstractContactDaoImpl<CONTACT extends Contact> extends AbstractCollectionItemDaoImpl<Contact,ContactCollection> implements AbstractContactDao<CONTACT>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	protected String readByValue,countByValue;
	
}
 