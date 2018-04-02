package org.cyk.system.root.business.impl.store;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.store.StoreBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.store.Store;
import org.cyk.system.root.model.store.StoreType;
import org.cyk.system.root.persistence.api.store.StoreDao;
import org.cyk.utility.common.helper.StringHelper;

public class StoreBusinessImpl extends AbstractDataTreeBusinessImpl<Store,StoreDao,StoreType> implements StoreBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public StoreBusinessImpl(StoreDao dao) {
        super(dao);
    } 
	
	@Override
	public Store instanciateOne() {
		Store store = super.instanciateOne();
		if(StringHelper.getInstance().isNotBlank(RootConstant.Code.getDefault(StoreType.class)))
			store.setType(read(StoreType.class, RootConstant.Code.getDefault(StoreType.class)));
		return store;
	}
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeBusinessImpl.BuilderOneDimensionArray<Store> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Store.class);
		}
		
	}
	
}
