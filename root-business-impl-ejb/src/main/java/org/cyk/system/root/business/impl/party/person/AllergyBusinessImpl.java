package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.AllergyBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.persistence.api.party.person.AllergyDao;

public class AllergyBusinessImpl extends AbstractEnumerationBusinessImpl<Allergy, AllergyDao> implements AllergyBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public AllergyBusinessImpl(AllergyDao dao) {
		super(dao); 
	}   
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<Allergy> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Allergy.class);
		}
	}
}
