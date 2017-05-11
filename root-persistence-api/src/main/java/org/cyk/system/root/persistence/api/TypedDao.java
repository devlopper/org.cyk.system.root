package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;

public interface TypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends TypedPersistenceService<IDENTIFIABLE,Long> {
	
	Collection<IDENTIFIABLE> readAllExclude(Collection<IDENTIFIABLE> identifiables);
	Long countAllExclude(Collection<IDENTIFIABLE> identifiables);
	
	Collection<IDENTIFIABLE> readByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers);
	Long countByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers);
	
	/**
	 * Read by global identifier value
	 * @param globalIdentifierCodeValue
	 * @return
	 */
	IDENTIFIABLE readByGlobalIdentifierValue(String globalIdentifierValue);
	
	Collection<IDENTIFIABLE> readByGlobalIdentifierOrderNumber(Long orderNumber);
	
	
	
	/**/
	
	
}
