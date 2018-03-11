package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.MovementActionBusiness;
import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.MovementCollectionType;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionTypeDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.ObjectFieldValues;

public class MovementCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MovementCollection,Movement, MovementCollectionDao,MovementDao,MovementBusiness> implements MovementCollectionBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementCollectionBusinessImpl(MovementCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	protected void afterCrud(MovementCollection movementCollection, Crud crud) {
		super.afterCrud(movementCollection, crud);
		if(Crud.CREATE.equals(crud)){
			if(movementCollection.getType()!=null && Boolean.TRUE.equals(movementCollection.getType().getAutomaticallyJoinIdentifiablePeriodCollection())){
				/*IdentifiablePeriodCollection identifiablePeriodCollection = inject(IdentifiablePeriodCollectionBusiness.class).instanciateOne();
				FieldHelper.getInstance().copy(movementCollection, identifiablePeriodCollection);
				identifiablePeriodCollection.setCode(identifiablePeriodCollection.getCode()+"1"+RandomHelper.getInstance().getAlphabetic(5));
				System.out.println("MovementCollectionBusinessImpl.afterCrud() : "+identifiablePeriodCollection.getCode());
				inject(IdentifiablePeriodCollectionBusiness.class).create(identifiablePeriodCollection);
				inject(IdentifiablePeriodCollectionIdentifiableGlobalIdentifierBusiness.class).create(new IdentifiablePeriodCollectionIdentifiableGlobalIdentifier(
						identifiablePeriodCollection, movementCollection));
				*/
			}
		}
	}
	
	@Override
	protected MovementCollection __instanciateOne__(ObjectFieldValues objectFieldValues) {
		MovementCollection movementCollection = super.__instanciateOne__(objectFieldValues);
		movementCollection.setType(inject(MovementCollectionTypeDao.class).readDefaulted());
		return movementCollection;
	}
	
	@Override
	public MovementCollection instanciateOne(String typeCode,BigDecimal value,AbstractIdentifiable join){
		MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne();
		movementCollection.setType(inject(MovementCollectionTypeDao.class).read(typeCode));
		movementCollection.setValue(value);
		return movementCollection;
	}
	
	@Override
	public Collection<MovementCollection> findByTypeByJoin(MovementCollectionType type, AbstractIdentifiable join) {
		return dao.readByTypeByJoin(type, join);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal computeValue(MovementCollection movementCollection, MovementAction movementAction,BigDecimal increment) {
		return inject(MovementActionBusiness.class).computeValue(movementAction, movementCollection.getValue(), increment);
	}
	
	public static class BuilderOneDimensionArray extends AbstractCollectionBusinessImpl.BuilderOneDimensionArray<MovementCollection> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementCollection.class);
			addParameterArrayElementString(MovementCollection.FIELD_VALUE,MovementCollection.FIELD_TYPE);
		}
		
	}

}
