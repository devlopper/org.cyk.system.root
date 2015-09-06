package org.cyk.system.root.business.impl;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

public class ResourceProducer {

    @PersistenceUnit
    @Produces 
    private EntityManagerFactory entityManagerFactory;
    
    @PersistenceContext
    @Produces
    private EntityManager entityManager;     
    
}
