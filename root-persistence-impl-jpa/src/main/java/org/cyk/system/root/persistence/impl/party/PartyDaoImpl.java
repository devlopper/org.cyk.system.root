package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.party.BusinessRoleDao;
import org.cyk.system.root.persistence.api.party.PartyDao;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.Utils;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.MethodHelper;

public class PartyDaoImpl extends AbstractPartyDaoImpl<Party> implements PartyDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;

	@Override
	public Collection<Party> readByIdentifiablesByBusinessRole(Collection<? extends AbstractIdentifiable> identifiables,BusinessRole businessRole) {
		return MethodHelper.getInstance().callGet(inject(PartyIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifiersByRole(Utils.getGlobalIdentfiers(identifiables)
				, businessRole), Party.class, PartyIdentifiableGlobalIdentifier.FIELD_PARTY) ;
	}
	
	@Override
	public Collection<Party> readByIdentifiablesByBusinessRoleCode(Collection<? extends AbstractIdentifiable> identifiables,String businessRoleCode) {
		return readByIdentifiablesByBusinessRole(identifiables,inject(BusinessRoleDao.class).read(businessRoleCode));
	}
	
	@Override
	public Long countByIdentifiablesByBusinessRole(Collection<? extends AbstractIdentifiable> identifiables,BusinessRole businessRole) {
		return inject(PartyIdentifiableGlobalIdentifierDao.class).countByIdentifiableGlobalIdentifiersByRole(Utils.getGlobalIdentfiers(identifiables), businessRole);
	}

	@Override
	public Long countByIdentifiablesByBusinessRoleCode(Collection<? extends AbstractIdentifiable> identifiables,String businessRoleCode) {
		return countByIdentifiablesByBusinessRole(identifiables,inject(BusinessRoleDao.class).read(businessRoleCode));
	}
	
	@Override
	public Collection<Party> readByIdentifiableByBusinessRole(AbstractIdentifiable identifiable,BusinessRole businessRole) {
		return MethodHelper.getInstance().callGet(inject(PartyIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifierByRole(identifiable
				.getGlobalIdentifier(), businessRole), Party.class, PartyIdentifiableGlobalIdentifier.FIELD_PARTY) ;
	}
	
	@Override
	public Long countByIdentifiableByBusinessRole(AbstractIdentifiable identifiable, BusinessRole businessRole) {
		return inject(PartyIdentifiableGlobalIdentifierDao.class).countByIdentifiableGlobalIdentifierByBusinessRole(identifiable.getGlobalIdentifier(), businessRole);
	}
	
	@Override
	public Long countByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable, String businessRoleCode) {
		return countByIdentifiableByBusinessRole(identifiable, inject(BusinessRoleDao.class).read(businessRoleCode));
	}

	@Override
	public Collection<Party> readByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable,String businessRoleCode) {
		return readByIdentifiableByBusinessRole(identifiable, inject(BusinessRoleDao.class).read(businessRoleCode));
	}

	@Override
	public Party readFirstByIdentifiableByBusinessRole(AbstractIdentifiable identifiable, BusinessRole businessRole) {
		return CollectionHelper.getInstance().getFirst(readByIdentifiableByBusinessRole(identifiable, businessRole));
	}

	@Override
	public Party readFirstByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable, String businessRoleCode) {
		return readFirstByIdentifiableByBusinessRole(identifiable, inject(BusinessRoleDao.class).read(businessRoleCode));
	}

}
 