package org.cyk.system.root.business.api.network;

import java.net.URL;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;

public interface UniformResourceLocatorBusiness extends AbstractEnumerationBusiness<UniformResourceLocator> {
    
	UniformResourceLocator instanciateOne(String name,String relativeUrl,String[] parameters);
	UniformResourceLocator instanciateOne(String relativeUrl,String[] parameters);
	
	UniformResourceLocator instanciateOneCrudOne(Class<? extends AbstractIdentifiable> identifiableClass,Crud crud,String[] parameters);
	UniformResourceLocator instanciateOneCrudMany(Class<? extends AbstractIdentifiable> identifiableClass,String[] parameters);
	Collection<UniformResourceLocator> instanciateManyCrud(Class<? extends AbstractIdentifiable> identifiableClass);
	
	UniformResourceLocator instanciateOneSelectOne(Class<? extends AbstractIdentifiable> identifiableClass,String actionIdentifier,String[] parameters);
	UniformResourceLocator instanciateOneSelectMany(Class<? extends AbstractIdentifiable> identifiableClass,String actionIdentifier,String[] parameters);
	
	Collection<UniformResourceLocator> instanciateManyBusinessCrud(Class<? extends AbstractIdentifiable> identifiableClass,Boolean list,Boolean edit,Boolean consult,Boolean createMany,String[] selectOneActionIdentifiers,String[] selectManyActionIdentifiers);
	
	UniformResourceLocator find(URL url);
	UniformResourceLocator find(URL url,Collection<UniformResourceLocator> uniformResourceLocators);

	Boolean isAccessible(URL url);
	Boolean isAccessible(URL url,Collection<UniformResourceLocator> uniformResourceLocators);
	
	String findPath(UniformResourceLocator uniformResourceLocator);
	
	/**/
	
	
	
}
