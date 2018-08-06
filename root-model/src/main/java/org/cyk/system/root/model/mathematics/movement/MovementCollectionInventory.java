package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE) @Accessors(chain=true)
public class MovementCollectionInventory extends AbstractMovementCollections<MovementCollectionInventoryItem> implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@ManyToOne @JoinColumn(name=COLUMN_MOVEMENT_GROUP) private MovementGroup movementGroup;
	@ManyToOne @JoinColumn(name=COLUMN_TYPE) private MovementCollectionInventoryType type;
	
	public MovementCollectionInventory setTypeFromCode(String code){
		type = getFromCode(MovementCollectionInventoryType.class, code);
		return this;
	}
	
	public MovementCollectionInventory setPartyFromCode(String code){
		return (MovementCollectionInventory) super.setPartyFromCode(code);
	}
	
	@Override
	public MovementCollectionInventory setItemsSynchonizationEnabled(Boolean synchonizationEnabled) {
		return (MovementCollectionInventory) super.setItemsSynchonizationEnabled(synchonizationEnabled);
	}
	
	@Override
	public MovementCollectionInventory addCascadeOperationToMasterFieldNames(String... fieldNames) {
		return (MovementCollectionInventory) super.addCascadeOperationToMasterFieldNames(fieldNames);
	}
	
	@Override
	public MovementCollectionInventory __setBirthDateComputedByUser__(Boolean value) {
		return (MovementCollectionInventory) super.__setBirthDateComputedByUser__(value);
	}
	
	@Override
	public MovementCollectionInventory setBirthDateFromString(String date) {
		return (MovementCollectionInventory) super.setBirthDateFromString(date);
	}
	
	public MovementCollectionInventory computeAndSetValueFromObject(Integer index,Object value){
		items.getAt(index).setValueFromObject(value);
		return this;
	}
	
	@Override
	public MovementCollectionInventory setItemsCountInterval(Interval itemsCountInterval) {
		return (MovementCollectionInventory) super.setItemsCountInterval(itemsCountInterval);
	}
	
	@Override
	public MovementCollectionInventory computeAndSetItemsCountIntervalFromCode(String code) {
		return (MovementCollectionInventory) super.computeAndSetItemsCountIntervalFromCode(code);
	}
	
	public MovementCollectionInventory computeChanges(){
		return (MovementCollectionInventory) super.computeChanges();
	}
	
	public MovementCollectionInventory computeAndSetItemValueFromObjectAt(Integer index,Object value){
		getItemAt(index).setValueFromObject(value);
		return this;
	}
	
	public MovementCollectionInventory addItems(Collection<MovementCollectionInventoryItem> items){
		getItems().addMany(items);
		return this;
	}
	
	@Override
	public MovementCollectionInventory set__identifiablePeriod__(IdentifiablePeriod identifiablePeriod) {
		return (MovementCollectionInventory) super.set__identifiablePeriod__(identifiablePeriod);
	}
	
	public MovementCollectionInventory set__identifiablePeriod__fromCode(String code){
		return (MovementCollectionInventory) super.set__identifiablePeriod__fromCode(code);
	}
	
	/**/
	
	public static final String FIELD_MOVEMENT_GROUP = "movementGroup";
	public static final String FIELD_TYPE = "type";
	
	public static final String COLUMN_MOVEMENT_GROUP = FIELD_MOVEMENT_GROUP;
	public static final String COLUMN_TYPE = FIELD_TYPE;
}

