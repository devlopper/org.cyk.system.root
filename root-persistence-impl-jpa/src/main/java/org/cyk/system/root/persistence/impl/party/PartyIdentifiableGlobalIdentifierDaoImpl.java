package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public class PartyIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<PartyIdentifiableGlobalIdentifier,PartyIdentifiableGlobalIdentifier.SearchCriteria> implements PartyIdentifiableGlobalIdentifierDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;

	@Override
	public PartyIdentifiableGlobalIdentifier readByPartyByIdentifiableGlobalIdentifierByRole(Party party,GlobalIdentifier globalIdentifier,BusinessRole role) {
		return CollectionHelper.getInstance().getFirst(readByFilter(new PartyIdentifiableGlobalIdentifier.Filter().addMaster(party).addMaster(role)
				.addMasterIdentifiableGlobalIdentifier(globalIdentifier)));
	}
	
	@Override
	public Collection<PartyIdentifiableGlobalIdentifier> readByIdentifiableGlobalIdentifierByRole(GlobalIdentifier globalIdentifier,BusinessRole role) {
		return readByFilter(new PartyIdentifiableGlobalIdentifier.Filter().addMaster(role).addMasterIdentifiableGlobalIdentifier(globalIdentifier));
	}
	
	@Override
	public Long countByIdentifiableGlobalIdentifierByBusinessRole(GlobalIdentifier globalIdentifier, BusinessRole businessRole) {
		return countByFilter(new PartyIdentifiableGlobalIdentifier.Filter().addMaster(businessRole).addMasterIdentifiableGlobalIdentifier(globalIdentifier));
	}
	
	@Override
	public Collection<PartyIdentifiableGlobalIdentifier> readByParty(Party party) {
		return readByFilter(new PartyIdentifiableGlobalIdentifier.Filter().addMaster(party));
	}
	
	@Override
	public Collection<PartyIdentifiableGlobalIdentifier> readByPartyByBusinessRole(Party party, BusinessRole role) {
		return readByFilter(new PartyIdentifiableGlobalIdentifier.Filter().addMaster(party).addMaster(role));
	}
	
	@Override
	protected void listenInstanciateJpqlBuilder(String name, JavaPersistenceQueryLanguage builder) {
		super.listenInstanciateJpqlBuilder(name, builder);
		if(readByFilter.equals(name)){
			builder.setFieldName(PartyIdentifiableGlobalIdentifier.FIELD_PARTY).where().and().in(AbstractIdentifiable.FIELD_IDENTIFIER);
			builder.setFieldName(PartyIdentifiableGlobalIdentifier.FIELD_BUSINESS_ROLE).where().and().in(AbstractIdentifiable.FIELD_IDENTIFIER);
		}else if(ArrayUtils.contains(new String[]{readWhereExistencePeriodFromDateIsLessThan,readWhereExistencePeriodFromDateIsGreaterThan}, name)){
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass,QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName,arguments);
		if(ArrayUtils.contains(new String[]{readByFilter,countByFilter}, queryName)){
			FilterHelper.Filter<T> filter = (Filter<T>) arguments[0];
			queryWrapper.parameterInIdentifiers(filter.filterMasters(Party.class),PartyIdentifiableGlobalIdentifier.FIELD_PARTY,AbstractIdentifiable.FIELD_IDENTIFIER);
			queryWrapper.parameterInIdentifiers(filter.filterMasters(BusinessRole.class),PartyIdentifiableGlobalIdentifier.FIELD_BUSINESS_ROLE,AbstractIdentifiable.FIELD_IDENTIFIER);
		}else if(ArrayUtils.contains(new String[]{readWhereExistencePeriodFromDateIsLessThan,countWhereExistencePeriodFromDateIsLessThan,readWhereExistencePeriodFromDateIsGreaterThan,countWhereExistencePeriodFromDateIsGreaterThan}, queryName)){
			
		} 
	}
}
 