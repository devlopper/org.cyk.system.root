package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeRoleBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeRoleDao;

import lombok.Getter;
import lombok.Setter;

public class PersonRelationshipTypeRoleBusinessImpl extends AbstractTypedBusinessService<PersonRelationshipTypeRole,PersonRelationshipTypeRoleDao> implements PersonRelationshipTypeRoleBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public PersonRelationshipTypeRoleBusinessImpl(PersonRelationshipTypeRoleDao dao) {
        super(dao);
    }
	
	@Override
	protected Object[] getPropertyValueTokens(PersonRelationshipTypeRole personRelationshipTypeRole, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, name))
			return new Object[]{personRelationshipTypeRole.getPersonRelationshipType(),personRelationshipTypeRole.getRole()};
		return super.getPropertyValueTokens(personRelationshipTypeRole, name);
	}
	
	@Override
	public PersonRelationshipTypeRole instanciateOne(String typeCode,String roleCode) {
		return instanciateOne(new String[]{null,null,typeCode,roleCode});
	}
	
	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<PersonRelationshipTypeRole> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(PersonRelationshipTypeRole.class);
			addFieldCodeName();
			addParameterArrayElementString(PersonRelationshipTypeRole.FIELD_PERSON_RELATIONSHIP_TYPE,PersonRelationshipTypeRole.FIELD_ROLE);
		}
		
	}
	
	@Getter @Setter
	public static class Details extends AbstractOutputDetails<PersonRelationshipTypeRole> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		public Details(PersonRelationshipTypeRole personRelationshipTypeRole) {
			super(personRelationshipTypeRole);
		}
		
	}
	
}
