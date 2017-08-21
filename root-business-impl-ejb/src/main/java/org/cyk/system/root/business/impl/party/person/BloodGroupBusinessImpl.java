package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.BloodGroupBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.persistence.api.party.person.BloodGroupDao;

public class BloodGroupBusinessImpl extends AbstractEnumerationBusinessImpl<BloodGroup, BloodGroupDao> implements BloodGroupBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public BloodGroupBusinessImpl(BloodGroupDao dao) {
		super(dao); 
	}   
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<BloodGroup> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(BloodGroup.class);
		}
	}
	
}
