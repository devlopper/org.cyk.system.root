package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

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
	protected void __load__(PersonExtendedInformations extendedInformations) {
		super.__load__(extendedInformations);
		if(extendedInformations.getLanguageCollection()!=null)
			extendedInformations.getLanguageCollection().getItems().setCollection(inject(LanguageCollectionItemBusiness.class)
				.findByCollection(extendedInformations.getLanguageCollection()));
	}
}
