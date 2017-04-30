package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;


public interface RemoteBusiness extends BusinessService {

    <T extends AbstractIdentifiable> Collection<T> find(Class<T> aClass);
    
    <T extends AbstractIdentifiable> void create(Class<T> aClass,Collection<T> collection);
    <T extends AbstractIdentifiable> void update(Class<T> aClass,Collection<T> collection);
    <T extends AbstractIdentifiable> void delete(Class<T> aClass,Collection<T> collection);
    <T extends AbstractIdentifiable> void save(Class<T> aClass,Collection<T> collection);
}
