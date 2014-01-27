package org.cyk.system.root.dao.api;

import java.util.Collection;

import org.cyk.system.root.model.IModel;

public interface IGenericDataAccess extends IDataAccess<IModel<Object>,Object> {
	
	<IDENTIFIABLE, IDENTIFIER> IDENTIFIABLE readByClass(Class<IDENTIFIABLE> aClass,Class<IDENTIFIER> aIdClass,String identifier);
	
	<IDENTIFIABLE> IDENTIFIABLE readByClass(Class<IDENTIFIABLE> aClass,String identifier);
	
	<IDENTIFIABLE> Collection<IDENTIFIABLE> readAllByClass(Class<IDENTIFIABLE> aClass);
 
}
