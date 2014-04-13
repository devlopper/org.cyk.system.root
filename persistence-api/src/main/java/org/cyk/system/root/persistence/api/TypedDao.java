package org.cyk.system.root.persistence.api;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface TypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends TypedPersistenceService<IDENTIFIABLE,Long> {
	
}
