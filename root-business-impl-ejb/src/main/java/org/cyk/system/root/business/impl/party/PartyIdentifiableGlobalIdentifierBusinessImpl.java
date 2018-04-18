package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.PartyIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;
import org.cyk.utility.common.helper.FieldHelper;

public class PartyIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<PartyIdentifiableGlobalIdentifier, PartyIdentifiableGlobalIdentifierDao,PartyIdentifiableGlobalIdentifier.SearchCriteria> implements PartyIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public PartyIdentifiableGlobalIdentifierBusinessImpl(PartyIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	@Override
	public Collection<PartyIdentifiableGlobalIdentifier> findByPartyByBusinessRole(Party party, BusinessRole role) {
		return dao.readByPartyByBusinessRole(party, role);
	}
	
	@Override
	public Collection<PartyIdentifiableGlobalIdentifier> findByIdentifiableGlobalIdentifierByBusinessRole(AbstractIdentifiable identifiable, BusinessRole businessRole) {
		return dao.readByIdentifiableGlobalIdentifierByRole(identifiable.getGlobalIdentifier(), businessRole);
	}
	
	@Override
	public Collection<PartyIdentifiableGlobalIdentifier> findByIdentifiableGlobalIdentifierByBusinessRoleCode(AbstractIdentifiable identifiable, String businessRoleCode) {
		return findByIdentifiableGlobalIdentifierByBusinessRole(identifiable, read(BusinessRole.class, businessRoleCode));
	}
	
	@Override
	public void deleteByPartyByBusinessRoleByIdentifiable(Party party,BusinessRole businessRole,AbstractIdentifiable identifiable) {
		Collection<PartyIdentifiableGlobalIdentifier> partyIdentifiableGlobalIdentifiers = dao.readByIdentifiableGlobalIdentifierByRole(identifiable.getGlobalIdentifier()
				, businessRole);
		Collection<PartyIdentifiableGlobalIdentifier> partyIdentifiableGlobalIdentifiersToDelete = new ArrayList<>();
		
		if(party==null){
			partyIdentifiableGlobalIdentifiersToDelete.addAll(partyIdentifiableGlobalIdentifiers);
		}else{
			for(PartyIdentifiableGlobalIdentifier index : partyIdentifiableGlobalIdentifiers)
				if(!index.getParty().equals(party))
					partyIdentifiableGlobalIdentifiersToDelete.add(index);
		}
		delete(partyIdentifiableGlobalIdentifiersToDelete);
	}
	
	@Override
	public void deleteByPartyFieldNameByBusinessRoleCodeByIdentifiable(String partyFieldName, String businessRoleCode,AbstractIdentifiable identifiable) {
		deleteByPartyByBusinessRoleByIdentifiable((Party)FieldHelper.getInstance().read(identifiable, partyFieldName)
				, read(BusinessRole.class, businessRoleCode), identifiable);
	}
}
