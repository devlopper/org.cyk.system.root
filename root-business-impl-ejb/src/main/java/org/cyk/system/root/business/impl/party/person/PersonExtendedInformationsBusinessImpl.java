package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.party.person.PersonExtendedInformationsBusiness;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.persistence.api.party.person.PersonExtendedInformationsDao;
import org.cyk.system.root.persistence.api.party.person.PersonTitleDao;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.generator.RandomDataProvider.RandomFile;

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
	public PersonExtendedInformations instanciateOneRandomly(Person person) {
		PersonExtendedInformations personExtendedInformations = instanciateOne();
		personExtendedInformations.setParty(person);
		personExtendedInformations.setTitle(inject(PersonTitleDao.class).readOneRandomly());
		personExtendedInformations.setSignatureSpecimen(new File());
		RandomFile randomFile = RandomDataProvider.getInstance().signatureSpecimen();
		inject(FileBusiness.class).process(personExtendedInformations.getSignatureSpecimen(), randomFile.getBytes(), "signature."+randomFile.getExtension());
		return personExtendedInformations;
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
