package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.party.AbstractPartyBusinessImpl;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.system.root.persistence.api.party.person.JobInformationsDao;
import org.cyk.system.root.persistence.api.party.person.MedicalInformationsDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.api.party.person.PersonExtendedInformationsDao;
import org.cyk.utility.common.Constant;

@Stateless
public class PersonBusinessImpl extends AbstractPartyBusinessImpl<Person, PersonDao,PersonSearchCriteria> implements PersonBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	//@Inject private RepeatedEventBusiness repeatedEventBusiness;
	
	@Inject private PersonExtendedInformationsDao extendedInformationsDao;
	@Inject private JobInformationsDao jobInformationsDao;
	@Inject private MedicalInformationsDao medicalInformationsDao;
	
	@Inject
	public PersonBusinessImpl(PersonDao dao) {
		super(dao); 
	}  
	
	@Override
	public Person create(Person person) {
		super.create(person);
		//person.setBirthDateAnniversary(repeatedEventBusiness.createAnniversary(person.getBirthDate(),person.getNames()));
		if(person.getExtendedInformations()!=null)
			extendedInformationsDao.create(person.getExtendedInformations());
		if(person.getJobInformations()!=null)
			jobInformationsDao.create(person.getJobInformations());
		if(person.getMedicalInformations()!=null)
			medicalInformationsDao.create(person.getMedicalInformations());
		return person;
	}
	
	@Override
	public Person update(Person person) {
		Person p = super.update(person);
		//repeatedEventBusiness.updateAnniversary(person.getBirthDateAnniversary(),person.getBirthDate(), person.getName());
		return p;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findNames(Person person,FindNamesOptions options) {
		List<String> blocks = new ArrayList<>();
		if(StringUtils.isNotBlank(person.getName()))
			blocks.add(person.getName());
		if(StringUtils.isNotBlank(person.getLastName()))
			blocks.add(person.getLastName());
		
		return StringUtils.join(blocks,Constant.CHARACTER_SPACE);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findNames(Person person) {
		return findNames(person, new FindNamesOptions());
	}
	
	@Override
	public void load(Person person) {
		super.load(person);
		person.setExtendedInformations(extendedInformationsDao.readByParty(person));
		person.setJobInformations(jobInformationsDao.readByParty(person));
		person.setMedicalInformations(medicalInformationsDao.readByParty(person));
	}
	
}
 