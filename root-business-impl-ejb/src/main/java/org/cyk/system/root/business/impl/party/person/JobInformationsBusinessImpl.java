package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.party.person.JobInformationsBusiness;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.persistence.api.party.person.JobInformationsDao;

public class JobInformationsBusinessImpl extends AbstractPersonExtendedInformationsBusinessImpl<JobInformations, JobInformationsDao> implements JobInformationsBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public JobInformationsBusinessImpl(JobInformationsDao dao) {
		super(dao); 
	}   
	
	@Override
	protected void __load__(JobInformations jobInformations) {
		super.__load__(jobInformations);
		inject(ContactCollectionBusiness.class).load(jobInformations.getContactCollection());	
	}
	
}
