package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageCollectionBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.RootDataProducerHelper;
import org.cyk.system.root.business.impl.party.AbstractPartyBusinessImpl;
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
import org.cyk.system.root.persistence.api.file.FileDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.language.LanguageCollectionDao;
import org.cyk.system.root.persistence.api.party.person.JobInformationsDao;
import org.cyk.system.root.persistence.api.party.person.MedicalInformationsDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.api.party.person.PersonExtendedInformationsDao;
import org.cyk.utility.common.Constant;

@Stateless
public class PersonBusinessImpl extends AbstractPartyBusinessImpl<Person, PersonDao,SearchCriteria> implements PersonBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	//@Inject private RepeatedEventBusiness repeatedEventBusiness;
	 
	@Inject private PersonExtendedInformationsDao extendedInformationsDao;
	@Inject private JobInformationsDao jobInformationsDao;
	@Inject private MedicalInformationsDao medicalInformationsDao;
	@Inject private ContactDao contactDao;
	@Inject private FileDao fileDao;
	@Inject private LanguageCollectionDao languageCollectionDao;
	
	@Inject
	public PersonBusinessImpl(PersonDao dao) {
		super(dao); 
	}  
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Person instanciateOne() {
		Person person = super.instanciateOne();
		person.setExtendedInformations(new PersonExtendedInformations(person));
		person.setJobInformations(new JobInformations(person));
		return person;
	}
	
	@Override
	public Person create(Person person) {
		super.create(person);
		//person.setBirthDateAnniversary(repeatedEventBusiness.createAnniversary(person.getBirthDate(),person.getNames()));
		
		if(person.getExtendedInformations()!=null){
			if(person.getExtendedInformations().getBirthLocation()!=null)
				contactDao.create(person.getExtendedInformations().getBirthLocation());
			if(person.getExtendedInformations().getLanguageCollection()!=null)
				inject(LanguageCollectionBusiness.class).create(person.getExtendedInformations().getLanguageCollection());
			extendedInformationsDao.create(person.getExtendedInformations());
		}
		if(person.getJobInformations()!=null)
			jobInformationsDao.create(person.getJobInformations());
		if(person.getMedicalInformations()!=null)
			medicalInformationsDao.create(person.getMedicalInformations());
		person = dao.update(person);
		
		return person;
	}
	
	@Override
	public Person update(Person person) {
		Person p = super.update(person);
		//repeatedEventBusiness.updateAnniversary(person.getBirthDateAnniversary(),person.getBirthDate(), person.getName());
		if(person.getExtendedInformations()!=null){
			if(person.getExtendedInformations().getBirthLocation()!=null)
				contactDao.update(person.getExtendedInformations().getBirthLocation());
			if(person.getExtendedInformations().getLanguageCollection()!=null)
				languageCollectionDao.update(person.getExtendedInformations().getLanguageCollection());
			if(person.getExtendedInformations().getSignatureSpecimen()!=null && person.getExtendedInformations().getSignatureSpecimen().getIdentifier()==null)
				fileDao.create(person.getExtendedInformations().getSignatureSpecimen());
			extendedInformationsDao.update(person.getExtendedInformations());
		}
		if(person.getJobInformations()!=null)
			jobInformationsDao.update(person.getJobInformations());
		if(person.getMedicalInformations()!=null)
			medicalInformationsDao.update(person.getMedicalInformations());
		return p;
	}
	
	@Override
	public Person delete(Person person) {
		PersonExtendedInformations extendedInformations = extendedInformationsDao.readByParty(person);
		if(extendedInformations!=null){
			if(extendedInformations.getLanguageCollection()!=null)
				languageCollectionDao.delete(extendedInformations.getLanguageCollection());
			extendedInformationsDao.delete(extendedInformations);
		}
		JobInformations jobInformations = jobInformationsDao.readByParty(person);
		if(jobInformations!=null){
			jobInformationsDao.delete(jobInformations);
		}
		MedicalInformations medicalInformations = medicalInformationsDao.readByParty(person);	
		if(medicalInformations!=null){
			medicalInformationsDao.delete(medicalInformations);
		}
		return super.delete(person);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findNames(Person person,FindNamesOptions options) {
		List<String> blocks = new ArrayList<>();
		if(StringUtils.isNotBlank(person.getName()))
			blocks.add(person.getName());
		if(StringUtils.isNotBlank(person.getLastnames()))
			blocks.add(person.getLastnames());
		
		if(Boolean.TRUE.equals(options.getFirstNameIsFirst()))
			;
		else
			Collections.reverse(blocks);
		
		person.setNames(StringUtils.join(blocks,Constant.CHARACTER_SPACE));
		return person.getNames();
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findNames(Person person) {
		return findNames(person, new FindNamesOptions());
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

}
 