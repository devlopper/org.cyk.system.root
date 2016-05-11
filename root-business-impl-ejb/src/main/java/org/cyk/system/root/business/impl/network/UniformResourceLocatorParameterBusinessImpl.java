package org.cyk.system.root.business.impl.network;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.network.UniformResourceLocatorParameterBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.persistence.api.network.UniformResourceLocatorParameterDao;

public class UniformResourceLocatorParameterBusinessImpl extends AbstractTypedBusinessService<UniformResourceLocatorParameter, UniformResourceLocatorParameterDao> implements UniformResourceLocatorParameterBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UniformResourceLocatorParameterBusinessImpl(UniformResourceLocatorParameterDao dao) {
		super(dao); 
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<UniformResourceLocatorParameter> findByUniformResourceLocator(UniformResourceLocator uniformResourceLocator) {
		return dao.readByUniformResourceLocator(uniformResourceLocator);
	}
	
	public static String getCrudAsString(Crud crud){
		crud=crud==null?Crud.READ:crud;
		switch(crud){
		case CREATE:return UniformResourceLocatorParameter.CRUD_CREATE;
		case READ:return UniformResourceLocatorParameter.CRUD_READ;
		case UPDATE:return UniformResourceLocatorParameter.CRUD_UPDATE;
		case DELETE:return UniformResourceLocatorParameter.CRUD_DELETE;
		default: return null;
		}
	}
	
	public static Crud getCrudAsObject(String value){
		switch(value){
		case UniformResourceLocatorParameter.CRUD_CREATE:return Crud.CREATE;
		case UniformResourceLocatorParameter.CRUD_READ:return Crud.READ;
		case UniformResourceLocatorParameter.CRUD_UPDATE:return Crud.UPDATE;
		case UniformResourceLocatorParameter.CRUD_DELETE:return Crud.DELETE;
		default: return null;
		}
	}
	

}
