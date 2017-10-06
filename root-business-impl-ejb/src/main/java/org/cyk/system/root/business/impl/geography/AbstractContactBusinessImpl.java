package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.geography.AbstractContactBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.persistence.api.geography.AbstractContactDao;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractContactBusinessImpl<CONTACT extends Contact,DAO extends AbstractContactDao<CONTACT>> extends AbstractCollectionItemBusinessImpl<Contact, DAO,ContactCollection> implements AbstractContactBusiness<CONTACT>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractContactBusinessImpl(DAO dao) {
		super(dao); 
	}

	@Override
	public Collection<CONTACT> readByValue(String value) {
		return dao.readByValue(value);
	}

	@Override
	public Long countByValue(String value) {
		return dao.countByValue(value);
	}
	
	@Override
	public Class<ContactCollection> getCollectionClass() {
		return ContactCollection.class;
	}
	
	@Getter @Setter
	public static class Details<T extends Contact> extends AbstractCollectionItemBusinessImpl.Details<T> implements Serializable {

		private static final long serialVersionUID = 4444472169870625893L;

		public Details(T identifiable) {
			super(identifiable);
		}
		
	}
}
