package org.cyk.system.root.business.api.mathematics.movement;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.party.Party;

public interface AbstractMovementCollectionsBusiness<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractCollectionBusiness<COLLECTION,ITEM> {

	Collection<MovementCollection> findMovementCollectionByParty(Party party);
	
}
