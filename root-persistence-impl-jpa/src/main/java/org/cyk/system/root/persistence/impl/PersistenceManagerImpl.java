package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.metamodel.EntityType;

import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.persistence.api.PersistenceManager;
import org.cyk.utility.common.cdi.AbstractBean;

public class PersistenceManagerImpl extends AbstractBean implements PersistenceManager, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceUnit 
    //@Inject 
    private EntityManagerFactory entityManagerFactory;
    
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Class<? extends Identifiable<?>>> findEntities() {
        Collection<Class<? extends Identifiable<?>>> l = new HashSet<>();
        for(EntityType<?> entityType : entityManagerFactory.getMetamodel().getEntities())
            l.add((Class<? extends Identifiable<?>>) entityType.getJavaType());
        return l;
    }

}
