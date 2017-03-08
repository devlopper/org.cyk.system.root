package org.cyk.system.root.persistence.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public interface MovementDao extends AbstractCollectionItemDao<Movement,MovementCollection> {

	Collection<Movement> readBySupportingDocumentIdentifier(String supportingDocumentIdentifier);
	Long countBySupportingDocumentIdentifier(String supportingDocumentIdentifier);
	
}
