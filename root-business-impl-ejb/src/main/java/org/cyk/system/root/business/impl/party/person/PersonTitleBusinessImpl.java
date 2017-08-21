package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonTitleBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.persistence.api.party.person.PersonTitleDao;

public class PersonTitleBusinessImpl extends AbstractEnumerationBusinessImpl<PersonTitle, PersonTitleDao> implements PersonTitleBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PersonTitleBusinessImpl(PersonTitleDao dao) {
		super(dao); 
	}   
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<PersonTitle> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(PersonTitle.class);
		}
	}
}
