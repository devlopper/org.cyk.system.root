package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.computation.Trigger;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.model.identifiable.Common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @MappedSuperclass @Accessors(chain=true)
public abstract class AbstractMovementCollections<ITEM extends AbstractEnumeration> extends AbstractCollection<ITEM> implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@Transient protected Party party;
	@Transient protected Collection<MovementCollection> movementCollections;
	
	public AbstractMovementCollections<ITEM> setPartyFromCode(String code){
		this.party = getFromCode(Party.class, code);
		return this;
	}
	
	@Override
	public AbstractMovementCollections<ITEM> setItemsSynchonizationEnabled(Boolean synchonizationEnabled) {
		return (AbstractMovementCollections<ITEM>) super.setItemsSynchonizationEnabled(synchonizationEnabled);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractMovementCollections<ITEM> addCascadeOperationToMasterFieldNames(String... fieldNames) {
		return (AbstractMovementCollections<ITEM>) super.addCascadeOperationToMasterFieldNames(fieldNames);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractMovementCollections<ITEM> __setBirthDateComputedByUser__(Boolean value) {
		return (AbstractMovementCollections<ITEM>) super.__setBirthDateComputedByUser__(value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractMovementCollections<ITEM> register(Trigger trigger, ClassHelper.Listener.FieldName fieldName) {
		return (AbstractMovementCollections<ITEM>) super.register(trigger, fieldName);
	}
	
	@Override
	public Common register(Trigger trigger, String... fieldNames) {
		// TODO Auto-generated method stub
		return super.register(trigger, fieldNames);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractMovementCollections<ITEM> setBirthDateFromString(String date) {
		return (AbstractMovementCollections<ITEM>) super.setBirthDateFromString(date);
	}
	
	/**/
	
	public static final String FIELD_PARTY = "party";
	
}

