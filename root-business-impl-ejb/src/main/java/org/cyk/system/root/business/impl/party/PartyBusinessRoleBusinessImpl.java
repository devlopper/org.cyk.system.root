package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.PartyBusinessRoleBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.party.PartyBusinessRole;
import org.cyk.system.root.persistence.api.party.PartyBusinessRoleDao;

public class PartyBusinessRoleBusinessImpl extends AbstractDataTreeTypeBusinessImpl<PartyBusinessRole,PartyBusinessRoleDao> implements PartyBusinessRoleBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public PartyBusinessRoleBusinessImpl(PartyBusinessRoleDao dao) {
        super(dao);
    }
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<PartyBusinessRole> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(PartyBusinessRole.class);
		}
		
	}

}
