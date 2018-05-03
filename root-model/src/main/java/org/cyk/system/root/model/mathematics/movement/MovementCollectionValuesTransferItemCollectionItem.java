package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) @Accessors(chain=true)
public class MovementCollectionValuesTransferItemCollectionItem extends AbstractCollectionItem<MovementCollectionValuesTransferItemCollection> implements Serializable {
	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne @JoinColumn(name=COLUMN_SOURCE) @NotNull private Movement source;
	@ManyToOne @JoinColumn(name=COLUMN_DESTINATION) @NotNull private Movement destination;
	
	@Transient private MovementCollectionValuesTransferItemCollectionItem transfered;
	@Transient private AbstractIdentifiable identifiableJoined;
	
	/**/
	
	public MovementCollectionValuesTransferItemCollectionItem setSourceMovementCollectionFromCode(String code){
		this.source.setCollectionFromCode(code);
		return this;
	}
	
	public MovementCollectionValuesTransferItemCollectionItem setDestinationMovementCollectionFromCode(String code){
		this.destination.setCollectionFromCode(code);
		return this;
	}
	
	public MovementCollectionValuesTransferItemCollectionItem setSourceValueAbsoluteFromObject(Object value){
		source.setValueAbsoluteFromObject(value);
		return this;
	}
	
	public MovementCollectionValuesTransferItemCollectionItem setDestinationValueAbsoluteFromObject(Object value){
		destination.setValueAbsoluteFromObject(value);
		return this;
	}
	
	public BigDecimal getValue(){
		if(source!=null)
			if(source.getValue()!=null)
				return source.getValue().abs();
			else
				return source.getValueAbsolute();
		if(destination!=null)
			if(destination.getValue()!=null)
				return destination.getValue();
			else
				return destination.getValueAbsolute();
		return null;
	}
	
	/**/
	
	public static final String FIELD_SOURCE = "source";
	public static final String FIELD_DESTINATION = "destination";
	
	public static final String COLUMN_SOURCE = FIELD_SOURCE;
	public static final String COLUMN_DESTINATION = FIELD_DESTINATION;
}
