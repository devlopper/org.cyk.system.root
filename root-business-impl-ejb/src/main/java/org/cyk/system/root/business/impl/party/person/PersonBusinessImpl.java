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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.LocationBusiness;
import org.cyk.system.root.business.api.language.LanguageCollectionBusiness;
import org.cyk.system.root.business.api.party.person.JobInformationsBusiness;
import org.cyk.system.root.business.api.party.person.MedicalInformationsBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonExtendedInformationsBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.party.AbstractPartyBusinessImpl;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.file.FileDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.party.person.JobFunctionDao;
import org.cyk.system.root.persistence.api.party.person.JobInformationsDao;
import org.cyk.system.root.persistence.api.party.person.JobTitleDao;
import org.cyk.system.root.persistence.api.party.person.MedicalInformationsDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.api.party.person.PersonExtendedInformationsDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.api.party.person.PersonTitleDao;
import org.cyk.system.root.persistence.api.party.person.SexDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.generator.RandomDataProvider.RandomFile;
import org.cyk.utility.common.generator.RandomDataProvider.RandomPerson;

@Stateless
public class PersonBusinessImpl extends AbstractPartyBusinessImpl<Person, PersonDao> implements PersonBusiness,Serializable {

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
	
	@Override
	protected Collection<? extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<?>> getListeners() {
		return Listener.COLLECTION;
	}
	
	@Override
	public Collection<Person> get(Collection<? extends AbstractActor> actors) {
		Collection<Person> persons = new ArrayList<>();
		for(AbstractActor actor : actors)
			persons.add(actor.getPerson());
		return persons;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Person instanciateOne() {
		Person person = super.instanciateOne();
		person.setExtendedInformations(new PersonExtendedInformations(person));
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
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
		person.setSex(inject(SexDao.class).read(Boolean.TRUE.equals(male)?RootConstant.Code.Sex.MALE:RootConstant.Code.Sex.FEMALE));
		person.setSurname(randomPerson.surName());
		person.setBirthDate(RandomDataProvider.getInstance().randomDate(DateUtils.addYears(new Date(), -50), DateUtils.addYears(new Date(), -20)) );
		person.setContactCollection(inject(ContactCollectionBusiness.class).instanciateOneRandomly());
		File photo = new File();
		RandomFile randomFile = randomPerson.photo();
		inject(FileBusiness.class).process(photo, randomFile.getBytes(), "photo."+randomFile.getExtension());
		person.setImage(photo);
		
		person.setBirthLocation((Location) inject(LocationBusiness.class).instanciateOneRandomly());
		
		person.getExtendedInformations().setTitle(inject(PersonTitleDao.class).readOneRandomly());
		person.getJobInformations().setTitle(inject(JobTitleDao.class).readOneRandomly());
		person.getJobInformations().setFunction(inject(JobFunctionDao.class).readOneRandomly());
		
		File signature = new File();
		randomFile = RandomDataProvider.getInstance().signatureSpecimen();
		inject(FileBusiness.class).process(signature, randomFile.getBytes(), "signature."+randomFile.getExtension());
		person.getExtendedInformations().setSignatureSpecimen(signature);
			
		return person;
	}
	
	/*@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PersonRelationship addRelationship(Person person, String relationshipTypeCode) {
		if(person.getRelationships()==null)
			person.setRelationships(new ArrayList<PersonRelationship>());
		else
			for(PersonRelationship personRelationship : person.getRelationships())
				if(personRelationship.getType().getCode().equals(relationshipTypeCode))
					return personRelationship;
		PersonRelationship personRelationship = null;//new PersonRelationship(instanciateOne(), inject(PersonRelationshipTypeDao.class).read(relationshipTypeCode), person);
		person.getRelationships().add(personRelationship);
		return personRelationship;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void addRelationships(Person person, Collection<String> relationshipTypeCodes) {
		for(String relationshipTypeCode : relationshipTypeCodes){
			addRelationship(person, relationshipTypeCode);
		}
	}*/
	
	@Override
	protected void beforeCreate(Person person) {
		super.beforeCreate(person);
		if(StringUtils.isEmpty(person.getCode()))
			person.setCode(RandomStringUtils.randomAlphanumeric(5));
		
		//exceptionUtils().exception(StringUtils.isBlank(person.getCode()), "person.code.required");
	}
	
	@Override
	protected void afterCreate(Person person) {
		super.afterCreate(person);
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
		
		if(person.getRelationships()!=null)
			for(PersonRelationship personRelationship : person.getRelationships())
				createIfNotIdentified(personRelationship);
		
	}
	
	@Override
	protected void beforeUpdate(Person person) {
		super.beforeUpdate(person);
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
		
		/*if(person.getRelationships()!=null)
			for(PersonRelationship personRelationship : person.getRelationships())
				if(isNotIdentified(personRelationship))
					inject(PersonRelationshipBusiness.class).create(personRelationship);
				else{
					update(personRelationship.getPerson1().equals(person)?personRelationship.getPerson2():personRelationship.getPerson1());
				}*/
					
	}
		
	@Override
	protected void beforeDelete(Person person) {
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
		
		super.beforeDelete(person);
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
	public Collection<Person> findByPersonByRelationshipTypeRole(String personCode, String personRelationshipTypeRoleCode) {
		Collection<Person> persons = new ArrayList<>();
		for(PersonRelationship personRelationship : inject(PersonRelationshipDao.class).readByPersonByRole(read(Person.class, personCode)
				, read(PersonRelationshipTypeRole.class,personRelationshipTypeRoleCode)))
			persons.add(personRelationship.getExtremity1().getPerson().getCode().equals(personCode) ? personRelationship.getExtremity2().getPerson() 
				: personRelationship.getExtremity1().getPerson() );	
		return persons;
	}
	
	/*
	@Override
	public Person findOneByPersonByRelationshipType(Person person, String personRelationshipTypeCode) {
		Collection<Person> persons = findByPersonByRelationshipType(person, personRelationshipTypeCode);
		exceptionUtils().exception(persons.size() > 1, "toomuch.person.found");
		return persons.isEmpty() ? null : persons.iterator().next();
	}
	
	@Override
	public Collection<Person> findByPersonRelationshipPerson2ByPersonRelationshipTypes(Collection<Person> persons, Collection<PersonRelationshipType> personRelationshipTypes) {
		Collection<Person> collection = new ArrayList<>();
		for(PersonRelationship personRelationship : inject(PersonRelationshipBusiness.class).findByPerson2ByTypes(persons, personRelationshipTypes))
			collection.add(personRelationship.getPerson1());
		return collection;
	}
	*/
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)  @Deprecated
	public void load(Person person) {
		super.load(person);
		person.setExtendedInformations(extendedInformationsDao.readByParty(person));
		person.setJobInformations(jobInformationsDao.readByParty(person));
		person.setMedicalInformations(medicalInformationsDao.readByParty(person));
	}
	
	@Override
	protected void setRelatedIdentifiable(Person person,String relatedIdentifiableFieldName) {
		if(Person.FIELD_EXTENDED_INFORMATIONS.equals(relatedIdentifiableFieldName)){
			person.setExtendedInformations(inject(PersonExtendedInformationsDao.class).readByParty(person));
		}else if(Person.FIELD_JOB_INFORMATIONS.equals(relatedIdentifiableFieldName)){
			person.setJobInformations(inject(JobInformationsDao.class).readByParty(person));
		}else if(Person.FIELD_MEDICAL_INFORMATIONS.equals(relatedIdentifiableFieldName)){
			person.setMedicalInformations(inject(MedicalInformationsDao.class).readByParty(person));
		}
	}
	
	@Override
	public void setRelatedIdentifiables(Person person, Boolean image,Boolean signature, String... relatedIdentifiableFieldNames) {
		if(Boolean.TRUE.equals(signature)){
			relatedIdentifiableFieldNames = ArrayUtils.add(relatedIdentifiableFieldNames, Person.FIELD_EXTENDED_INFORMATIONS);
		}
		setRelatedIdentifiables(person, image, relatedIdentifiableFieldNames);
		if(Boolean.TRUE.equals(signature)){
			inject(FileBusiness.class).findInputStream(person.getExtendedInformations().getSignatureSpecimen(), Boolean.TRUE);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T extends AbstractIdentifiable> void setFieldValue(Object master,Class<T> fieldType,String fieldName,Integer index,String[] values){
		T fieldValue = (T) commonUtils.readField(master, commonUtils.getFieldFromClass(master.getClass(), fieldName), Boolean.FALSE);
		if(index!=null){
			if(fieldValue==null){
				String code = values[index];
				fieldValue = inject(PersistenceInterfaceLocator.class).injectTyped(fieldType).read(code);
				if(fieldValue==null){
					TypedBusiness<T> business = inject(BusinessInterfaceLocator.class).injectTyped(fieldType);
					if(AbstractEnumeration.class.isAssignableFrom(fieldType))
						fieldValue = (T) ((AbstractEnumerationBusiness)business).instanciateOne(code);
					else
						fieldValue = business.instanciateOne();
				}
			}
		}
	}
	
	@Override
	protected Class<? extends AbstractFieldValueSearchCriteriaSet> getSearchCriteriaClass() {
		return Person.SearchCriteria.class;
	}
	
	/**/

	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<Person>{
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/

		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter<Person> implements Listener, Serializable {
			private static final long serialVersionUID = -1625238619828187690L;
			
		}
		
	}
	
	/**/
	
}
 