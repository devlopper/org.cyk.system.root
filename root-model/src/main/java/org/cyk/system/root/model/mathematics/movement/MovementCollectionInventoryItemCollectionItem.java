package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@Column(name=COLUMN_VALUE,precision=20,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value;
	
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
	public static final String FIELD_VALUE = "value";
	
	public static final String COLUMN_MOVEMENT_COLLECTION = FIELD_MOVEMENT_COLLECTION;
	public static final String COLUMN_VALUE = FIELD_VALUE;
	
}
