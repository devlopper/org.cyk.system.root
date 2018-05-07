package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE) @Accessors(chain=true)
public class MovementCollectionInventory extends AbstractCollection<MovementCollectionInventoryItem> implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@ManyToOne @JoinColumn(name=COLUMN_MOVEMENT_GROUP) private MovementGroup movementGroup;
	@ManyToOne @JoinColumn(name=COLUMN_TYPE) private MovementCollectionInventoryType type;
	
	@Transient private Party party;
	@Transient private Collection<MovementCollection> movementCollections;
	
	public MovementCollectionInventory setPartyFromCode(String code){
		this.party = getFromCode(Party.class, code);
		return this;
	}
	
	@Override
	public MovementCollectionInventory setItemsSynchonizationEnabled(Boolean synchonizationEnabled) {
		return (MovementCollectionInventory) super.setItemsSynchonizationEnabled(synchonizationEnabled);
	}
	
	@Override
	public MovementCollectionInventory addCascadeOperationToMasterFieldNames(String... fieldNames) {
		return (MovementCollectionInventory) super.addCascadeOperationToMasterFieldNames(fieldNames);
	}
	
	/**/
	
	public static final String FIELD_MOVEMENT_GROUP = "movementGroup";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_PARTY = "party";
	
	public static final String COLUMN_MOVEMENT_GROUP = FIELD_MOVEMENT_GROUP;
	public static final String COLUMN_TYPE = FIELD_TYPE;
}

