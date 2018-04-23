package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) @Accessors(chain=true)
public class MovementCollectionInventoryItemCollectionItem extends AbstractCollectionItem<MovementCollectionInventoryItemCollection> implements Serializable {
	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne @JoinColumn(name=COLUMN_MOVEMENT_COLLECTION) @NotNull private MovementCollection movementCollection;
	@ManyToOne @JoinColumn(name=COLUMN_MOVEMENT_BEFORE_EXISTENCE) private Movement movementBeforeExistence;
	@Column(name=COLUMN_VALUE,precision=20,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value;
	@ManyToOne @JoinColumn(name=COLUMN_VALUE_GAP_MOVEMENT) private Movement valueGapMovement;
	
	@Transient private BigDecimal valueGap;
	
	public MovementCollectionInventoryItemCollectionItem setMovementCollectionFromCode(String code){
		this.movementCollection = getFromCode(MovementCollection.class, code);
		return this;
	}
	
	public MovementCollectionInventoryItemCollectionItem setValueFromObject(Object value){
		this.value = getNumberFromObject(BigDecimal.class, value);
		return this;
	}
	
	/**/
	
	public static final String FIELD_MOVEMENT_COLLECTION = "movementCollection";
	public static final String FIELD_MOVEMENT_BEFORE_EXISTENCE = "movementBeforeExistence";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_VALUE_GAP_MOVEMENT = "valueGapMovement";
	public static final String FIELD_VALUE_GAP = "valueGap";
	
	public static final String COLUMN_MOVEMENT_COLLECTION = FIELD_MOVEMENT_COLLECTION;
	public static final String COLUMN_MOVEMENT_BEFORE_EXISTENCE = FIELD_MOVEMENT_BEFORE_EXISTENCE;
	public static final String COLUMN_VALUE = FIELD_VALUE;
	public static final String COLUMN_VALUE_GAP_MOVEMENT = FIELD_VALUE_GAP_MOVEMENT;
	
}
