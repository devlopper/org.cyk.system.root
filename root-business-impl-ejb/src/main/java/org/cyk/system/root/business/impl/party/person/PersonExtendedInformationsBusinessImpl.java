package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.language.LanguageCollectionItemBusiness;
import org.cyk.system.root.business.api.party.person.PersonExtendedInformationsBusiness;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.persistence.api.party.person.PersonExtendedInformationsDao;

public class PersonExtendedInformationsBusinessImpl extends AbstractPersonExtendedInformationsBusinessImpl<PersonExtendedInformations, PersonExtendedInformationsDao> implements PersonExtendedInformationsBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PersonExtendedInformationsBusinessImpl(PersonExtendedInformationsDao dao) {
		super(dao); 
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(PersonExtendedInformations personExtendedInformations) {
		return Arrays.asList(PersonExtendedInformations.FIELD_LANGUAGE_COLLECTION,PersonExtendedInformations.FIELD_SIGNATURE_SPECIMEN);
	}
		
	@Override
	protected void __load__(PersonExtendedInformations extendedInformations) {
		super.__load__(extendedInformations);
		if(extendedInformations.getLanguageCollection()!=null)
			extendedInformations.getLanguageCollection().getItems().setCollection(inject(LanguageCollectionItemBusiness.class)
				.findByCollection(extendedInformations.getLanguageCollection()));
	}
	
	@Override
	protected Collection<? extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<?>> getListeners() {
		return Listener.COLLECTION;
	}
	
	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<PersonExtendedInformations>{
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/

		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter<PersonExtendedInformations> implements Listener, Serializable {
			private static final long serialVersionUID = -1625238619828187690L;
			
		}
		
	}
}
