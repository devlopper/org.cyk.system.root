package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.JobTitleBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.persistence.api.party.person.JobTitleDao;

public class JobTitleBusinessImpl extends AbstractEnumerationBusinessImpl<JobTitle, JobTitleDao> implements JobTitleBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public JobTitleBusinessImpl(JobTitleDao dao) {
		super(dao); 
	}   
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<JobTitle> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(JobTitle.class);
		}
	}
	
}
