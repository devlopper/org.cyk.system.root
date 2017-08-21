package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeGroupBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeGroupDao;

public class PersonRelationshipTypeGroupBusinessImpl extends AbstractDataTreeTypeBusinessImpl<PersonRelationshipTypeGroup,PersonRelationshipTypeGroupDao> implements PersonRelationshipTypeGroupBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public PersonRelationshipTypeGroupBusinessImpl(PersonRelationshipTypeGroupDao dao) {
        super(dao);
    }
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<PersonRelationshipTypeGroup> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(PersonRelationshipTypeGroup.class);
		}
		
	}

}
