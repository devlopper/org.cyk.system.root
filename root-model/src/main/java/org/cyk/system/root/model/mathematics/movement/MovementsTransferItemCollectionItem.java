package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) @Accessors(chain=true)
public class MovementsTransferItemCollectionItem extends AbstractCollectionItem<MovementsTransferItemCollection> implements Serializable {
	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne @JoinColumn(name=COLUMN_SOURCE) @NotNull private Movement source;
	@ManyToOne @JoinColumn(name=COLUMN_DESTINATION) @NotNull private Movement destination;
	
	@Transient private MovementCollection sourceMovementCollection;
	@Transient private MovementCollection destinationMovementCollection;
	@Transient private BigDecimal value;
	
	/**/
	
	public MovementsTransferItemCollectionItem setSourceMovementCollectionFromCode(String code){
		this.sourceMovementCollection = getFromCode(MovementCollection.class, code);
		return this;
	}
	
	public MovementsTransferItemCollectionItem setDestinationMovementCollectionFromCode(String code){
		this.destinationMovementCollection = getFromCode(MovementCollection.class, code);
		return this;
	}
	
	public MovementsTransferItemCollectionItem setValueFromObject(Object value){
		this.value = getNumberFromObject(BigDecimal.class, value);
		return this;
	}
	
	/**/
	
	public static final String FIELD_SOURCE = "source";
	public static final String FIELD_DESTINATION = "destination";
	public static final String FIELD_SOURCE_MOVEMENT_COLLECTION = "sourceMovementCollection";
	public static final String FIELD_DESTINATION_MOVEMENT_COLLECTION = "destinationMovementCollection";
	public static final String FIELD_VALUE = "value";
	
	public static final String COLUMN_SOURCE = FIELD_SOURCE;
	public static final String COLUMN_DESTINATION = FIELD_DESTINATION;
}
