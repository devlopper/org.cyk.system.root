package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.movement.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionTypeDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.ConditionHelper.Condition;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;

public class MovementCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MovementCollection,Movement, MovementCollectionDao,MovementDao,MovementBusiness> implements MovementCollectionBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementCollectionBusinessImpl(MovementCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	public MovementCollection instanciateOne() {
		return super.instanciateOne().setType(inject(MovementCollectionTypeDao.class).readDefaulted());
	}
	
	@Override
	protected void createMaster(MovementCollection movementCollection,String fieldName, AbstractIdentifiable master) {
		super.createMaster(movementCollection,fieldName, master);
		if(master instanceof MovementCollection && MovementCollection.FIELD_BUFFER.equals(fieldName)){
			master.setCode(movementCollection.getCode()+"BUFFER");
			master.setName(movementCollection.getName()+" buffer");
			((MovementCollection)master).setType(movementCollection.getType());
		}
	}
	
	@Override
	protected void beforeCrud(MovementCollection movementCollection, Crud crud) {
		super.beforeCrud(movementCollection, crud);
		if(movementCollection.getType()!=null && movementCollection.getType().getInterval()!=null)
			throw__(Condition.getBuildersDoesNotBelongsTo(movementCollection
					, movementCollection.getType().getInterval().getLow().getValueWithoutExcludedInformation()
					, movementCollection.getType().getInterval().getHigh().getValueWithoutExcludedInformation(), MovementCollection.FIELD_VALUE));
		
	}
	
	@Override
	protected void afterCrud(MovementCollection movementCollection, Crud crud) {
		super.afterCrud(movementCollection, crud);
		if(Crud.isCreateOrUpdate(crud)){
			if(movementCollection.getType()!=null && Boolean.TRUE.equals(movementCollection.getType().getValueIsAggregated()) 
					&& Boolean.TRUE.equals(movementCollection.getItemAggregationApplied())){
				throw__(ConditionHelper.Condition.getBuilderComparison(movementCollection, computeValue(movementCollection), null, Boolean.FALSE
						, MovementCollection.FIELD_VALUE) );
			}
		}
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
		}else if(Crud.DELETE.equals(crud)){
			if(movementCollection.getBuffer()!=null)
				delete(movementCollection.getBuffer());
		}
	}
	
	@Override
	protected MovementCollection __instanciateOne__(ObjectFieldValues objectFieldValues) {
		MovementCollection movementCollection = super.__instanciateOne__(objectFieldValues);
		movementCollection.setType(inject(MovementCollectionTypeDao.class).readDefaulted());
		return movementCollection;
	}
	
	@Override
	public Collection<MovementCollection> findByTypeByJoin(MovementCollectionType type, AbstractIdentifiable join) {
		return dao.readByTypeByJoin(type, join);
	}
	
	@Override
	public BigDecimal computeValue(MovementCollection movementCollection) {
		if(isNotIdentified(movementCollection))
			return movementCollection.getValue();
		return InstanceHelper.getInstance().getIfNotNullElseDefault(NumberHelper.getInstance().get(BigDecimal.class, NumberHelper.getInstance().add(inject(MovementDao.class)
				.sumValueByCollection(movementCollection), movementCollection.getInitialValue())),BigDecimal.ZERO);
	}
	
	@Override
	protected void computeChanges(MovementCollection movementCollection, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollection, loggingMessageBuilder);		
		if(isNotIdentified(movementCollection)){
			if(movementCollection.getInitialValue() == null)
				movementCollection.setInitialValue(movementCollection.getValue());
			
		}		
	}
	
	@Override
	protected void beforeDelete(MovementCollection movementCollection) {
		super.beforeDelete(movementCollection);
		inject(MovementCollectionIdentifiableGlobalIdentifierBusiness.class).delete(inject(MovementCollectionIdentifiableGlobalIdentifierDao.class)
				.readByMovementCollection(movementCollection));
	}
	
	public static class BuilderOneDimensionArray extends AbstractCollectionBusinessImpl.BuilderOneDimensionArray<MovementCollection> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementCollection.class);
			addParameterArrayElementString(MovementCollection.FIELD_VALUE,MovementCollection.FIELD_TYPE);
		}
		
	}

}
