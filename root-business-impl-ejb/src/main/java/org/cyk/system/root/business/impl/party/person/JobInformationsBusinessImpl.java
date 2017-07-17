package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.party.person.JobInformationsBusiness;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.JobFunctionDao;
import org.cyk.system.root.persistence.api.party.person.JobInformationsDao;
import org.cyk.system.root.persistence.api.party.person.JobTitleDao;
import org.cyk.utility.common.helper.CollectionHelper;

public class JobInformationsBusinessImpl extends AbstractPersonExtendedInformationsBusinessImpl<JobInformations, JobInformationsDao> implements JobInformationsBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public JobInformationsBusinessImpl(JobInformationsDao dao) {
		super(dao); 
	}   
	
	@Override
	public JobInformations instanciateOneRandomly(Person person) {
		JobInformations jobInformations = instanciateOne();
		jobInformations.setParty(person);
		jobInformations.setCompany(RandomStringUtils.randomAlphabetic(10));
		jobInformations.setContactCollection(inject(ContactCollectionBusiness.class).instanciateOneRandomly());
		jobInformations.setFunction(inject(JobFunctionDao.class).readOneRandomly());
		jobInformations.setTitle(inject(JobTitleDao.class).readOneRandomly());
		return jobInformations;
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(JobInformations jobInformations) {
		return new CollectionHelper().add(super.findRelatedInstanceFieldNames(jobInformations), Boolean.FALSE, JobInformations.FIELD_CONTACT_COLLECTION);
	}
	
}
