package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE) @Accessors(chain=true)
public class MovementCollectionValuesTransferItemCollection extends AbstractMovementCollections<MovementCollectionValuesTransferItemCollectionItem> implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=MovementCollectionValuesTransferItemCollectionExtremity.FIELD_MOVEMENT_COLLECTION_IS_BUFFER,column=@Column(name=COLUMN_MOVEMENT_COLLECTION_IS_BUFFER_SOURCE))
	})
	private MovementCollectionValuesTransferItemCollectionExtremity source;
	
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=MovementCollectionValuesTransferItemCollectionExtremity.FIELD_MOVEMENT_COLLECTION_IS_BUFFER,column=@Column(name=COLUMN_MOVEMENT_COLLECTION_IS_BUFFER_DESTINATION))
	})
	private MovementCollectionValuesTransferItemCollectionExtremity destination;
	
	public MovementCollectionValuesTransferItemCollectionExtremity getSource(){
		if(this.source == null)
			this.source = new MovementCollectionValuesTransferItemCollectionExtremity();
		return this.source;
	}
	
	public MovementCollectionValuesTransferItemCollectionExtremity getDestination(){
		if(this.destination == null)
			this.destination = new MovementCollectionValuesTransferItemCollectionExtremity();
		return this.destination;
	}
	
	public MovementCollectionValuesTransferItemCollection setSourceMovementCollectionIsBuffer(Boolean sourceMovementCollectionIsBuffer){
		getSource().setMovementCollectionIsBuffer(sourceMovementCollectionIsBuffer);
		return this;
	}
	
	public MovementCollectionValuesTransferItemCollection setDestinationMovementCollectionIsBuffer(Boolean destinationMovementCollectionIsBuffer){
		getDestination().setMovementCollectionIsBuffer(destinationMovementCollectionIsBuffer);
		return this;
	}
	
	@Override
	public MovementCollectionValuesTransferItemCollection setItemsSynchonizationEnabled(Boolean synchonizationEnabled) {
		return (MovementCollectionValuesTransferItemCollection) super.setItemsSynchonizationEnabled(synchonizationEnabled);
	}
	
	public MovementCollectionValuesTransferItemCollection addBySourceMovementCollectionCodeByDestinationMovementCollectionCodeByValue
		(String sourceMovementCollectionCode,String destinationMovementCollectionCode,Object value){
		add(instanciateOne(MovementCollectionValuesTransferItemCollectionItem.class)
				.setSourceMovementCollectionFromCode(sourceMovementCollectionCode).setDestinationMovementCollectionFromCode(destinationMovementCollectionCode)
				.setSourceValueAbsoluteFromObject(value));
		return this;
	}
	
	public MovementCollectionValuesTransferItemCollection computeAndSetDestinationMovementCollectionAndValueBySourceMovementCollectionCode
	(String sourceMovementCollectionCode,String destinationMovementCollectionCode,Object value,String reasonCode){
		for(MovementCollectionValuesTransferItemCollectionItem index : getItems().getElements()){
			if(index.getSource().getCollection().getCode().equals(sourceMovementCollectionCode)){
				index.setDestinationMovementCollectionFromCode(destinationMovementCollectionCode).setSourceValueAbsoluteFromObject(value)
				;
				index.getDestination().setValueSettableFromAbsolute(Boolean.TRUE).computeAndSetActionFromIncrementation(Boolean.TRUE)
					.setReasonFromCode(reasonCode);
				break;
			}
		}
	return this;
}
	
	@Override
	public MovementCollectionValuesTransferItemCollection setPartyFromCode(String code) {
		return (MovementCollectionValuesTransferItemCollection) super.setPartyFromCode(code);
	}
	
	/**/
	
	public static final String FIELD_SOURCE = "source";
	public static final String FIELD_DESTINATION = "destination";
	
	public static final String COLUMN_MOVEMENT_COLLECTION_IS_BUFFER_SOURCE = FIELD_SOURCE+MovementCollectionValuesTransferItemCollectionExtremity.COLUMN_MOVEMENT_COLLECTION_IS_BUFFER;
	
	public static final String COLUMN_MOVEMENT_COLLECTION_IS_BUFFER_DESTINATION = FIELD_DESTINATION+MovementCollectionValuesTransferItemCollectionExtremity.COLUMN_MOVEMENT_COLLECTION_IS_BUFFER;
}

