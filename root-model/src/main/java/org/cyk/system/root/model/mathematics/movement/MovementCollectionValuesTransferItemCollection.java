package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class MovementCollectionValuesTransferItemCollection extends AbstractCollection<MovementCollectionValuesTransferItemCollectionItem> implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

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
	
}

