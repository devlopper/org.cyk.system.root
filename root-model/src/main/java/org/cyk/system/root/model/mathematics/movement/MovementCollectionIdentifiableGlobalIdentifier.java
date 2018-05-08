package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A join between a movement collection and an identifiable
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION
		,MovementCollectionIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER})})
public class MovementCollectionIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {
	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @JoinColumn(name=COLUMN_MOVEMENT_COLLECTION) @NotNull private MovementCollection movementCollection;
	
	/**/
	
	public MovementCollectionIdentifiableGlobalIdentifier(MovementCollection movementCollection,AbstractIdentifiable identifiable){
		super(identifiable);
		this.movementCollection = movementCollection;
	}
	
	public MovementCollectionIdentifiableGlobalIdentifier setMovementCollectionFromCode(String code){
		this.movementCollection = getFromCode(MovementCollection.class, code);
		return this;
	}
	
	/**/
	
	@Override
	public String toString() {
		return identifiableGlobalIdentifier.getCode()+" "+movementCollection;
	}
	
	/**/
	
	public static final String FIELD_MOVEMENT_COLLECTION = "movementCollection";
	
	public static final String COLUMN_MOVEMENT_COLLECTION = FIELD_MOVEMENT_COLLECTION;
	
	/**/
	
	public static class Filter extends AbstractJoinGlobalIdentifier.Filter<MovementCollectionIdentifiableGlobalIdentifier> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
		@Override
		public Filter addMaster(Object master) {
			return (Filter) super.addMaster(master);
		}
		
		@Override
		public Filter addMasterIdentifiableGlobalIdentifier(GlobalIdentifier... masterIdentifiableGlobalIdentifiers) {
			return (Filter) super.addMasterIdentifiableGlobalIdentifier(masterIdentifiableGlobalIdentifiers);
		}
		
    }
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		//private Collection<MovementCollectionType> movementCollectionTypes = new ArrayList<>();
		
		@Override
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			return (SearchCriteria) super.addGlobalIdentifiers(globalIdentifiers);
		}
		
		@Override
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return (SearchCriteria) super.addGlobalIdentifier(globalIdentifier);
		}
		
		@Override
		public SearchCriteria addIdentifiablesGlobalIdentifiers(Collection<? extends AbstractIdentifiable> identifiables) {
			return (SearchCriteria) super.addIdentifiablesGlobalIdentifiers(identifiables);
		}
		
		@Override
		public SearchCriteria addIdentifiableGlobalIdentifier(AbstractIdentifiable identifiable) {
			return (SearchCriteria) super.addIdentifiableGlobalIdentifier(identifiable);
		}

		/*public SearchCriteria addMovementCollectionTypes(Collection<MovementCollectionType> movementCollectionTypes){
			this.movementCollectionTypes.addAll(movementCollectionTypes);
			return this;
		}
		
		public SearchCriteria addMovementCollectionType(MovementCollectionType movementCollectionType){
			return addMovementCollectionTypes(Arrays.asList(movementCollectionType));
		}*/
		
		
	}
	
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(MovementCollectionIdentifiableGlobalIdentifier.class, aClass);
	}
	
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(MovementCollectionIdentifiableGlobalIdentifier.class,aClass);
	}
	
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(MovementCollectionIdentifiableGlobalIdentifier.class,object);
	}
	
}