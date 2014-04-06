package org.cyk.system.root.dao.api;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface TypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends TypedPersistenceService<IDENTIFIABLE,Long> {
	
}
