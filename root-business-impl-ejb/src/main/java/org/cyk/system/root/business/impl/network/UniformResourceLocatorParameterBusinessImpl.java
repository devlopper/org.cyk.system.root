package org.cyk.system.root.business.impl.network;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.network.UniformResourceLocatorParameterBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
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
		if(value==null)
			return null;
		switch(value){
		case UniformResourceLocatorParameter.CRUD_CREATE:return Crud.CREATE;
		case UniformResourceLocatorParameter.CRUD_READ:return Crud.READ;
		case UniformResourceLocatorParameter.CRUD_UPDATE:return Crud.UPDATE;
		case UniformResourceLocatorParameter.CRUD_DELETE:return Crud.DELETE;
		default: return null;
		}
	}
	
	public static String getParameter(String name,Map<String, String[]> map){
		String[] list = map.get(name);
		if(list==null || list.length==0)
			return null;
		return list[0];
	}
	
	public static CommonBusinessAction getCommonBusinessAction(URL url,Map<String, String[]> parameters) {
		Crud crud = getCrudAsObject(getParameter(UniformResourceLocatorParameter.CRUD,parameters));
		if(crud==null)
			if(StringUtils.endsWith(url.getPath(), UniformResourceLocatorBusiness.FILE_CONSULT+UniformResourceLocatorBusiness.PROCESSED_FILE_EXTENSION))
				crud = Crud.READ;
		CommonBusinessAction commonBusinessAction = null;
		if(crud!=null)
			commonBusinessAction = CommonBusinessAction.valueOf(crud.name());
		return commonBusinessAction;
	}
	
	@SuppressWarnings("unchecked")
	public static Class<? extends AbstractIdentifiable> getIdentifiableClass(URL url,Map<String, String[]> parameters) {
		Class<? extends AbstractIdentifiable> clazz = null;
		String classParameterValue = getParameter(UniformResourceLocatorParameter.CLASS,parameters);
		if(StringUtils.isNotBlank(classParameterValue))
			clazz = (Class<? extends AbstractIdentifiable>) RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfos(classParameterValue).getClazz();
		return clazz;
	}
	
	public static AbstractIdentifiable getIdentifiableInstance(URL url,Map<String, String[]> parameters) {
		AbstractIdentifiable identifiable = null;
		String identifierParameterValue = getParameter(UniformResourceLocatorParameter.IDENTIFIABLE,parameters);
		if(StringUtils.isNotBlank(identifierParameterValue)){
			Class<? extends AbstractIdentifiable> clazz = getIdentifiableClass(url, parameters);
			if(clazz!=null){
				identifiable = RootBusinessLayer.getInstance().getGenericBusiness().use(clazz).find(Long.parseLong(identifierParameterValue));
			}
		}
		return identifiable;
	}
	

}
