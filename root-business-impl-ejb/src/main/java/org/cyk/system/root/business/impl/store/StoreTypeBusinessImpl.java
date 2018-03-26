package org.cyk.system.root.business.impl.store;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.store.StoreTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.store.StoreType;
import org.cyk.system.root.persistence.api.store.StoreTypeDao;

import lombok.Getter;
import lombok.Setter;
 
public class StoreTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<StoreType,StoreTypeDao> implements StoreTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public StoreTypeBusinessImpl(StoreTypeDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<StoreType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(StoreType.class);
		}
		
	}
	
	@Getter @Setter
	public static class Details extends AbstractDataTreeTypeBusinessImpl.Details<StoreType> implements Serializable {

		private static final long serialVersionUID = -4747519269632371426L;

		public Details(StoreType localityType) {
			super(localityType);
		}
		
	}

}
