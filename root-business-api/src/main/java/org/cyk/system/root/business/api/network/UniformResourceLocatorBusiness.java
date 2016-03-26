package org.cyk.system.root.business.api.network;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;

import lombok.Getter;
import lombok.Setter;

public interface UniformResourceLocatorBusiness extends AbstractEnumerationBusiness<UniformResourceLocator> {
    
	Environment ENVIRONMENT = new Environment();
	
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
	UniformResourceLocator save(UniformResourceLocator uniformResourceLocator,Collection<UniformResourceLocatorParameter> parameters);
	
	/**/
	
	@Getter @Setter
	public static class Environment implements Serializable {

		private static final long serialVersionUID = 1276033839426765782L;

		private String privateFolderName="private";
		
		private String fileExtension = "jsf";
		private String fileList = "list";
		private String fileEdit = "edit";
		private String fileConsult = "consult";
		private String fileCreateMany = "createMany";
		
		private String urlDynamicCrudOne = "/private/__tools__/crud/crudone.jsf";
		private String urlDynamicCrudMany = "/private/__tools__/crud/crudmany.jsf";
		private String urlDynamicSelectOne = "/private/__tools__/selectone.jsf";
		private String urlDynamicSelectMany = "/private/__tools__/selectmany.jsf";
		
		private String parameterClass="clazz";
		private String parameterCrud="crud";
		private String parameterCrudCreate="create";
		private String parameterCrudRead="read";
		private String parameterCrudUpdate="update";
		private String parameterCrudDelete="delete";
		private String parameterActionIdentifier="actid";
		
		public String getParameterCrudValue(Crud crud){
			crud=crud==null?Crud.READ:crud;
			switch(crud){
			case CREATE:return parameterCrudCreate;
			case READ:return parameterCrudRead;
			case UPDATE:return parameterCrudUpdate;
			case DELETE:return parameterCrudDelete;
			default: return null;
			}
		}
		
	}
}
