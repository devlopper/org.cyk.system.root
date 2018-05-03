package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.party.PartyBusiness;
import org.cyk.system.root.business.api.party.PartyIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.party.StoreBusiness;
import org.cyk.system.root.business.impl.helper.FieldHelper;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.model.party.StoreType;
import org.cyk.system.root.persistence.api.party.PartyDao;
import org.cyk.system.root.persistence.api.party.StoreDao;
import org.cyk.utility.common.helper.InstanceHelper;

public class StoreBusinessImpl extends AbstractDataTreeBusinessImpl<Store,StoreDao,StoreType> implements StoreBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public StoreBusinessImpl(StoreDao dao) {
        super(dao);
    } 
	
	@Override
	public Store instanciateOne() {
		Store store = super.instanciateOne();//.addCascadeOperationToMasterFieldNames(Store.FIELD_PARTY_COMPANY);
		store.setType(InstanceHelper.getInstance().getDefaultUsingBusinessIdentifier(StoreType.class));
		return store;
	}
	
	@Override
	protected void beforeCrud(Store store, Crud crud) {
		super.beforeCrud(store, crud);
		if(Boolean.TRUE.equals(store.getHasPartyAsCompany())){
			if(store.getPartyCompany() == null){				
				if(Crud.CREATE.equals(crud) || (Crud.UPDATE.equals(crud) && (inject(PartyDao.class).countByIdentifiableByBusinessRoleCode(store, RootConstant.Code.BusinessRole.COMPANY) == 0))){
					store.setPartyCompany(instanciateOne(Party.class));
					FieldHelper.getInstance().copy(store, store.getPartyCompany(),Boolean.FALSE
							,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
									AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE),org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
											AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
					inject(PartyBusiness.class).create(store.getPartyCompany());
				}
			}
		}
		store.addIdentifiablesPartyIdentifiableGlobalIdentifierFromField(Store.FIELD_PARTY_COMPANY,RootConstant.Code.BusinessRole.COMPANY);
	}
	
	@Override
	protected void afterUpdate(Store store) {
		super.afterUpdate(store);
		inject(PartyIdentifiableGlobalIdentifierBusiness.class).deleteByPartyFieldNameByBusinessRoleCodeByIdentifiable(Store.FIELD_PARTY_COMPANY
				, RootConstant.Code.BusinessRole.COMPANY, store);
	}
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeBusinessImpl.BuilderOneDimensionArray<Store> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Store.class);
		}
		
	}
	
}
