package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.LocationBusiness;
import org.cyk.system.root.business.api.language.LanguageCollectionBusiness;
import org.cyk.system.root.business.api.party.person.JobInformationsBusiness;
import org.cyk.system.root.business.api.party.person.MedicalInformationsBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonExtendedInformationsBusiness;
import org.cyk.system.root.business.impl.RootDataProducerHelper;
import org.cyk.system.root.business.impl.party.AbstractPartyBusinessImpl;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Person.SearchCriteria;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.file.FileDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.party.person.JobFunctionDao;
import org.cyk.system.root.persistence.api.party.person.JobInformationsDao;
import org.cyk.system.root.persistence.api.party.person.JobTitleDao;
import org.cyk.system.root.persistence.api.party.person.MedicalInformationsDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.api.party.person.PersonExtendedInformationsDao;
import org.cyk.system.root.persistence.api.party.person.PersonTitleDao;
import org.cyk.system.root.persistence.api.party.person.SexDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.generator.RandomDataProvider.RandomFile;
import org.cyk.utility.common.generator.RandomDataProvider.RandomPerson;

@Stateless
public class PersonBusinessImpl extends AbstractPartyBusinessImpl<Person, PersonDao,SearchCriteria> implements PersonBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	//@Inject private RepeatedEventBusiness repeatedEventBusiness;
	 
	@Inject private PersonExtendedInformationsDao extendedInformationsDao;
	@Inject private JobInformationsDao jobInformationsDao;
	@Inject private MedicalInformationsDao medicalInformationsDao;
	@Inject private ContactDao contactDao;
	@Inject private FileDao fileDao;
	
	@Inject
	public PersonBusinessImpl(PersonDao dao) {
		super(dao); 
	}  
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Person instanciateOne() {
		Person person = super.instanciateOne();
		person.setExtendedInformations(new PersonExtendedInformations(person));
		/*person.setJobInformations(new JobInformations(person));
		person.setMedicalInformations(new MedicalInformations(person));
		*/
		return person;
	}
	
	@Override
	public Person instanciateOne(final UserAccount userAccount) {
		beforeInstanciateOne(Listener.COLLECTION, userAccount);
		Person person = super.instanciateOne(userAccount);
		afterInstanciateOne(Listener.COLLECTION, userAccount, person);
		return person;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Person instanciateOne(String code, String[] names) {
		Person person = instanciateOne();
		person.setCode(code);
		person.setName(commonUtils.getValueAt(names, 0));
		person.setLastnames(commonUtils.getValueAt(names, 1));
		return person;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Person instanciateOneRandomly() {
		Boolean male = RandomDataProvider.getInstance().randomBoolean();
		RandomPerson randomPerson = Boolean.TRUE.equals(male)?RandomDataProvider.getInstance().getMale()
				:RandomDataProvider.getInstance().getFemale();
		Person person = new Person();
		person.getGlobalIdentifierCreateIfNull();
		person.setExtendedInformations(new PersonExtendedInformations(person));
		person.setJobInformations(new JobInformations(person));
		
		person.setName(randomPerson.firstName());
		person.setLastnames(randomPerson.lastName());
		person.setSex(inject(SexDao.class).read(Boolean.TRUE.equals(male)?Sex.MALE:Sex.FEMALE));
		person.setSurname(randomPerson.surName());
		person.setBirthDate(RandomDataProvider.getInstance().randomDate(DateUtils.addYears(new Date(), -50), DateUtils.addYears(new Date(), -20)) );
		person.setContactCollection(inject(ContactCollectionBusiness.class).instanciateOneRandomly());
		File photo = new File();
		RandomFile randomFile = randomPerson.photo();
		photo.setBytes(randomFile.getBytes());
		photo.setExtension(randomFile.getExtension());
		person.setImage(photo);
		
		person.getExtendedInformations().setBirthLocation((Location) inject(LocationBusiness.class).instanciateOneRandomly());
		person.getExtendedInformations().setTitle(inject(PersonTitleDao.class).readOneRandomly());
		person.getJobInformations().setTitle(inject(JobTitleDao.class).readOneRandomly());
		person.getJobInformations().setFunction(inject(JobFunctionDao.class).readOneRandomly());
		
		File signature = new File();
		randomFile = RandomDataProvider.getInstance().signatureSpecimen();
		signature.setBytes(randomFile.getBytes());
		signature.setExtension(randomFile.getExtension());
		person.getExtendedInformations().setSignatureSpecimen(signature);
			
		return person;
	}
	
	@Override
	public Person create(final Person person) {
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.beforeCreate(person);
			}
		});
		super.create(person);
		//person.setBirthDateAnniversary(repeatedEventBusiness.createAnniversary(person.getBirthDate(),person.getNames()));
		
		if(person.getExtendedInformations()!=null){
			if(person.getExtendedInformations().getBirthLocation()!=null)
				contactDao.create(person.getExtendedInformations().getBirthLocation());
			if(person.getExtendedInformations().getLanguageCollection()!=null)
				inject(LanguageCollectionBusiness.class).create(person.getExtendedInformations().getLanguageCollection());
			extendedInformationsDao.create(person.getExtendedInformations());
		}
		if(person.getJobInformations()!=null){
			if(person.getJobInformations().getContactCollection()!=null){
				if(StringUtils.isEmpty(person.getJobInformations().getContactCollection().getName()))
					person.getJobInformations().getContactCollection().setName(person.getName()+Constant.CHARACTER_UNDESCORE+StringUtils.defaultIfBlank(person.getJobInformations().getCompany()
							,Constant.EMPTY_STRING));
				inject(ContactCollectionBusiness.class).create(person.getJobInformations().getContactCollection());
			}
			jobInformationsDao.create(person.getJobInformations());
		}
		if(person.getMedicalInformations()!=null)
			medicalInformationsDao.create(person.getMedicalInformations());
		dao.update(person);
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.afterCreate(person);
			}
		});
		return person;
	}
	
	@Override
	public Person update(final Person person) {
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.beforeUpdate(person);
			}
		});
		Person p = super.update(person);
		//repeatedEventBusiness.updateAnniversary(person.getBirthDateAnniversary(),person.getBirthDate(), person.getName());
		if(person.getExtendedInformations()!=null){
			if(person.getExtendedInformations().getBirthLocation()!=null)
				contactDao.update(person.getExtendedInformations().getBirthLocation());
			if(person.getExtendedInformations().getLanguageCollection()!=null)
				inject(LanguageCollectionBusiness.class).update(person.getExtendedInformations().getLanguageCollection());
			if(person.getExtendedInformations().getSignatureSpecimen()!=null && person.getExtendedInformations().getSignatureSpecimen().getIdentifier()==null)
				fileDao.create(person.getExtendedInformations().getSignatureSpecimen());
			inject(PersonExtendedInformationsBusiness.class).update(person.getExtendedInformations());
		}
		if(person.getJobInformations()!=null){
			if(person.getJobInformations().getContactCollection()!=null){
				person.getJobInformations().getContactCollection().setName(person.getName()+Constant.CHARACTER_UNDESCORE+StringUtils.defaultIfBlank(person.getJobInformations().getCompany()
						,Constant.EMPTY_STRING));
				inject(ContactCollectionBusiness.class).update(person.getJobInformations().getContactCollection());
			}
			inject(JobInformationsBusiness.class).update(person.getJobInformations());
		}
		if(person.getMedicalInformations()!=null)
			inject(MedicalInformationsBusiness.class).update(person.getMedicalInformations());
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.afterUpdate(person);
			}
		});
		return p;
	}
	
	@Override
	public Person delete(final Person person) {
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.beforeDelete(person);
			}
		});
		PersonExtendedInformations extendedInformations = extendedInformationsDao.readByParty(person);
		if(extendedInformations!=null){
			if(extendedInformations.getLanguageCollection()!=null){
				inject(LanguageCollectionBusiness.class).delete(extendedInformations.getLanguageCollection());
				extendedInformations.setLanguageCollection(null);
			}
			extendedInformationsDao.delete(extendedInformations);
		}
		JobInformations jobInformations = jobInformationsDao.readByParty(person);
		if(jobInformations!=null){
			if(jobInformations.getContactCollection()!=null)
	    		inject(ContactCollectionBusiness.class).delete(jobInformations.getContactCollection());
			jobInformations.setContactCollection(null);
			jobInformationsDao.delete(jobInformations);
		}
		MedicalInformations medicalInformations = medicalInformationsDao.readByParty(person);	
		if(medicalInformations!=null){
			medicalInformationsDao.delete(medicalInformations);
		}
		super.delete(person);
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.afterDelete(person);
			}
		});
		return person;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findNames(Person person,FindNamesArguments arguments) {
		List<String> blocks = new ArrayList<>();
		if(StringUtils.isNotBlank(person.getName()))
			blocks.add(person.getName());
		if(StringUtils.isNotBlank(person.getLastnames()))
			blocks.add(person.getLastnames());
		
		if(Boolean.TRUE.equals(arguments.getFirstNameIsFirst()))
			;
		else
			Collections.reverse(blocks);
		
		person.setNames(StringUtils.join(blocks,Constant.CHARACTER_SPACE));
		return person.getNames();
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findNames(Person person) {
		return findNames(person, new FindNamesArguments());
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findInitials(Person person, FindInitialsArguments arguments) {
		Collection<String> collection = new ArrayList<>();
		String names = findNames(person,arguments.getFindNamesArguments());
		for(String string : StringUtils.split(names,Constant.CHARACTER_SPACE))
			collection.add(string.substring(0, 1));
		return StringUtils.join(collection,arguments.getSeparator()).toUpperCase();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findInitials(Person person) {
		return findInitials(person, new FindInitialsArguments());
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void load(Person person) {
		super.load(person);
		person.setExtendedInformations(extendedInformationsDao.readByParty(person));
		person.setJobInformations(jobInformationsDao.readByParty(person));
		person.setMedicalInformations(medicalInformationsDao.readByParty(person));
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void completeInstanciationOfOne(Person person) {
		super.completeInstanciationOfOne(person);
		if(person.getSex()!=null && person.getSex().getIdentifier()==null)
			person.setSex(RootDataProducerHelper.getInstance().getEnumeration(Sex.class, person.getSex().getCode()));
		if(person.getExtendedInformations()!=null && person.getExtendedInformations().getIdentifier()==null){
			PersonExtendedInformations personExtendedInformations = person.getExtendedInformations();
			if(personExtendedInformations.getTitle()!=null && personExtendedInformations.getTitle().getIdentifier()==null)
				personExtendedInformations.setTitle(RootDataProducerHelper.getInstance().getEnumeration(PersonTitle.class, personExtendedInformations.getTitle().getCode()));
			if(personExtendedInformations.getBirthLocation()!=null){
				//Location location = personExtendedInformations.getBirthLocation();
				
			}
			
		}
		
	}

	@Override
	public void completeInstanciationOfOneFromValues(Person person,AbstractCompleteInstanciationOfOneFromValuesArguments<Person> completeInstanciationOfOneFromValuesArguments) {
		CompletePersonInstanciationOfOneFromValuesArguments arguments = (CompletePersonInstanciationOfOneFromValuesArguments) completeInstanciationOfOneFromValuesArguments;
		super.completeInstanciationOfOneFromValues(person,arguments.getPartyInstanciationOfOneFromValuesArguments());
		
		if(arguments.getBirthLocationOtherDetailsIndex()!=null){
			if(getExtendedInformations(person).getBirthLocation()==null)
				person.getExtendedInformations().setBirthLocation(new Location());
			getExtendedInformations(person).getBirthLocation().setOtherDetails(arguments.getValues()[arguments.getBirthLocationOtherDetailsIndex()]);
		}
		
		if(arguments.getJobFunctionCodeIndex()!=null){
			if(getJobInformations(person).getFunction()==null)
				getJobInformations(person).setFunction(new JobFunction());
			getJobInformations(person).getFunction().setCode(arguments.getValues()[arguments.getJobFunctionCodeIndex()]);
		}
		
		if(arguments.getJobTitleCodeIndex()!=null){
			if(getJobInformations(person).getTitle()==null)
				getJobInformations(person).setTitle(new JobTitle());
			getJobInformations(person).getTitle().setCode(arguments.getValues()[arguments.getJobTitleCodeIndex()]);
		}
		
		if(arguments.getLastnameIndex()!=null){
			person.setLastnames(arguments.getValues()[arguments.getLastnameIndex()]);
		}
		
		if(arguments.getSexCodeIndex()!=null){
			if(person.getSex()==null)
				person.setSex(new Sex());
			person.getSex().setCode(arguments.getValues()[arguments.getSexCodeIndex()]);
		}
		
		if(arguments.getTitleCodeIndex()!=null){
			if(getExtendedInformations(person).getTitle()==null)
				person.getExtendedInformations().setTitle(new PersonTitle());
			getExtendedInformations(person).getTitle().setCode(arguments.getValues()[arguments.getTitleCodeIndex()]);
		}
		
		completeInstanciationOfOne(person);
		
		completeInstanciationOfOneFromValuesAfterProcessing(person,arguments.getValues(),arguments.getListener());
	}
	
	@Override
	public void completeInstanciationOfManyFromValues(List<Person> persons,AbstractCompleteInstanciationOfManyFromValuesArguments<Person> completeInstanciationOfManyFromValuesArguments) {
		CompletePersonInstanciationOfManyFromValuesArguments arguments = (CompletePersonInstanciationOfManyFromValuesArguments) completeInstanciationOfManyFromValuesArguments;
		completeInstanciationOfManyFromValuesBeforeProcessing(persons,arguments.getValues(), arguments.getListener());
		for(int index = 0; index < arguments.getValues().size(); index++ ){
			arguments.getInstanciationOfOneFromValuesArguments().setValues(arguments.getValues().get(index));
			completeInstanciationOfOneFromValues(persons.get(index), arguments.getInstanciationOfOneFromValuesArguments());
		}
		completeInstanciationOfManyFromValuesAfterProcessing(persons,arguments.getValues(), arguments.getListener());
	}

	private JobInformations getJobInformations(Person person){
		if(person.getJobInformations()==null)
			person.setJobInformations(new JobInformations(person));
		return person.getJobInformations();
	}
	
	private PersonExtendedInformations getExtendedInformations(Person person){
		if(person.getExtendedInformations()==null)
			person.setExtendedInformations(new PersonExtendedInformations(person));
		return person.getExtendedInformations();
	}

	/**/
	
	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<Person>{
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/

		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter<Person> implements Listener, Serializable {
			private static final long serialVersionUID = -1625238619828187690L;
			
		}
		
	}
}
 