package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.BusinessRoleBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.persistence.api.party.BusinessRoleDao;

public class BusinessRoleBusinessImpl extends AbstractDataTreeTypeBusinessImpl<BusinessRole,BusinessRoleDao> implements BusinessRoleBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public BusinessRoleBusinessImpl(BusinessRoleDao dao) {
        super(dao);
    }
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<BusinessRole> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(BusinessRole.class);
		}
		
	}

}
