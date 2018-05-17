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
public class MovementCollectionInventoryItem extends AbstractCollectionItem<MovementCollectionInventory> implements Serializable {
	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne @JoinColumn(name=COLUMN_MOVEMENT_COLLECTION) @NotNull private MovementCollection movementCollection;
	//@ManyToOne @JoinColumn(name=COLUMN_MOVEMENT_BEFORE_EXISTENCE) private Movement movementBeforeExistence;
	@Column(name=COLUMN_VALUE,precision=20,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value;
	@ManyToOne @JoinColumn(name=COLUMN_VALUE_GAP_MOVEMENT_GROUP_ITEM) private MovementGroupItem valueGapMovementGroupItem;
	//@ManyToOne @JoinColumn(name=COLUMN_VALUE_GAP_MOVEMENT) private Movement valueGapMovement;
	
	@Transient private BigDecimal previousValue;
	@Transient private BigDecimal valueGap;
	
	@Override
	public MovementCollectionInventoryItem setCollection(MovementCollectionInventory collection) {
		return (MovementCollectionInventoryItem) super.setCollection(collection);
	}
	
	@Override
	public MovementCollectionInventoryItem setCollectionFromCode(String code) {
		return (MovementCollectionInventoryItem) super.setCollectionFromCode(code);
	}
	
	public MovementCollectionInventoryItem setMovementCollectionFromCode(String code){
		this.movementCollection = getFromCode(MovementCollection.class, code);
		return this;
	}
	
	public MovementCollectionInventoryItem setValueFromObject(Object value){
		this.value = getNumberFromObject(BigDecimal.class, value);
		return this;
	}
	
	public BigDecimal getValueGap(){
		if(this.valueGap == null && valueGapMovementGroupItem!=null && valueGapMovementGroupItem.getMovement()!=null){
			this.valueGap = valueGapMovementGroupItem.getMovement().getValue();
		}
		return this.valueGap;
	}
	
	/**/
	
	public static final String FIELD_MOVEMENT_COLLECTION = "movementCollection";
	//public static final String FIELD_MOVEMENT_BEFORE_EXISTENCE = "movementBeforeExistence";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_VALUE_GAP_MOVEMENT_GROUP_ITEM = "valueGapMovementGroupItem";
	public static final String FIELD_VALUE_GAP_MOVEMENT = "valueGapMovement";
	public static final String FIELD_VALUE_GAP = "valueGap";
	public static final String FIELD_PREVIOUS_VALUE = "previousValue";
	
	public static final String COLUMN_MOVEMENT_COLLECTION = FIELD_MOVEMENT_COLLECTION;
	//public static final String COLUMN_MOVEMENT_BEFORE_EXISTENCE = FIELD_MOVEMENT_BEFORE_EXISTENCE;
	public static final String COLUMN_VALUE = FIELD_VALUE;
	public static final String COLUMN_VALUE_GAP_MOVEMENT_GROUP_ITEM = FIELD_VALUE_GAP_MOVEMENT_GROUP_ITEM;
	public static final String COLUMN_VALUE_GAP_MOVEMENT = FIELD_VALUE_GAP_MOVEMENT;
	
}
