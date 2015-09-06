package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface TypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends TypedPersistenceService<IDENTIFIABLE,Long> {
	
	Collection<IDENTIFIABLE> readAllExclude(Collection<IDENTIFIABLE> identifiables);
	Long countAllExclude(Collection<IDENTIFIABLE> identifiables);
}
