package org.cyk.system.root.business.impl.party;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.PartyBusinessRoleBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.PartyBusinessRole;
import org.cyk.system.root.persistence.api.party.PartyBusinessRoleDao;

public class PartyBusinessRoleBusinessImpl extends AbstractTypedBusinessService<PartyBusinessRole,PartyBusinessRoleDao> implements PartyBusinessRoleBusiness {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public PartyBusinessRoleBusinessImpl(PartyBusinessRoleDao dao) {
        super(dao);
    }

}
