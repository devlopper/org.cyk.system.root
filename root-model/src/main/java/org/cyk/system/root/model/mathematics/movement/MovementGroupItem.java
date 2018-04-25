package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

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
public class MovementGroupItem extends AbstractCollectionItem<MovementGroup> implements Serializable {
	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne @JoinColumn(name=COLUMN_MOVEMENT) @NotNull private Movement movement;
	
	public MovementGroupItem setMovementValueFromObject(Object value){
		getMovement(Boolean.TRUE).setValueFromObject(value);
		return this;
	}
	
	@Override
	public MovementGroupItem setCollection(MovementGroup collection) {
		return (MovementGroupItem) super.setCollection(collection);
	}
	
	@Override
	public MovementGroupItem setCollectionFromCode(String code) {
		return (MovementGroupItem) super.setCollectionFromCode(code);
	}
	
	public Movement getMovement(Boolean instanciateIfValueIsNull){
		return readFieldValue(FIELD_MOVEMENT, instanciateIfValueIsNull);
	}
	
	public MovementGroupItem setMovementCollection(MovementCollection collection) {
		getMovement(Boolean.TRUE).setCollection(collection);
		return this;
	}
	
	public MovementGroupItem setMovementCollectionFromCode(String code) {
		getMovement(Boolean.TRUE).setCollectionFromCode(code);
		return this;
	}
	
	/**/
	
	public static final String FIELD_MOVEMENT = "movement";
	
	public static final String COLUMN_MOVEMENT = FIELD_MOVEMENT;
	
}
