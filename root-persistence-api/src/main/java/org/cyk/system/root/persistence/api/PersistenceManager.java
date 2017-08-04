package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;

/**
 * Persistence manager
 * @author Christian Yao Komenan
 *
 */
public interface PersistenceManager {

    
    Collection<Class<? extends Identifiable<?>>> findEntities();
    
}
