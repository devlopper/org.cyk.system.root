package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.IdentifiablePeriodBusiness;
import org.cyk.system.root.business.api.time.IdentifiablePeriodCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodCollectionDao;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodCollectionTypeDao;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodDao;
import org.cyk.utility.common.ObjectFieldValues;

public class IdentifiablePeriodCollectionBusinessImpl extends AbstractCollectionBusinessImpl<IdentifiablePeriodCollection,IdentifiablePeriod, IdentifiablePeriodCollectionDao,IdentifiablePeriodDao,IdentifiablePeriodBusiness> implements IdentifiablePeriodCollectionBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public IdentifiablePeriodCollectionBusinessImpl(IdentifiablePeriodCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	protected IdentifiablePeriodCollection __instanciateOne__(ObjectFieldValues objectFieldValues) {
		IdentifiablePeriodCollection movementCollection = super.__instanciateOne__(objectFieldValues);
		movementCollection.setType(inject(IdentifiablePeriodCollectionTypeDao.class).readDefaulted());
		return movementCollection;
	}
	
	@Override
	public Collection<IdentifiablePeriodCollection> findByTypeByJoin(IdentifiablePeriodCollectionType type, AbstractIdentifiable join) {
		return dao.readByTypeByJoin(type, join);
	}
		
	public static class BuilderOneDimensionArray extends AbstractCollectionBusinessImpl.BuilderOneDimensionArray<IdentifiablePeriodCollection> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(IdentifiablePeriodCollection.class);
	
		}
		
	}

}
