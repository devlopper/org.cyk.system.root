package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.JobFunctionBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.persistence.api.party.person.JobFunctionDao;

public class JobFunctionBusinessImpl extends AbstractEnumerationBusinessImpl<JobFunction, JobFunctionDao> implements JobFunctionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public JobFunctionBusinessImpl(JobFunctionDao dao) {
		super(dao); 
	}   
	
}
