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
public class MovementsTransferItemCollection extends AbstractCollection<MovementsTransferItemCollectionItem> implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@Override
	public MovementsTransferItemCollection setItemsSynchonizationEnabled(Boolean synchonizationEnabled) {
		return (MovementsTransferItemCollection) super.setItemsSynchonizationEnabled(synchonizationEnabled);
	}
	
	public MovementsTransferItemCollection addBySourceMovementCollectionCodeByDestinationMovementCollectionCodeByValue
		(String sourceMovementCollectionCode,String destinationMovementCollectionCode,Object value){
		add(instanciateOne(MovementsTransferItemCollectionItem.class)
				.setSourceMovementCollectionFromCode(sourceMovementCollectionCode).setDestinationMovementCollectionFromCode(destinationMovementCollectionCode)
				.setValueFromObject(value));
		return this;
	}
	
}

