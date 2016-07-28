package org.cyk.system.root.business.impl.network;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessExceptionNoRollBack;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.persistence.api.network.UniformResourceLocatorDao;
import org.cyk.system.root.persistence.api.network.UniformResourceLocatorParameterDao;
import org.cyk.utility.common.Constant;

@Stateless
public class UniformResourceLocatorBusinessImpl extends AbstractEnumerationBusinessImpl<UniformResourceLocator, UniformResourceLocatorDao> implements UniformResourceLocatorBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private UniformResourceLocatorParameterDao uniformResourceLocatorParameterDao;
	
	@Inject
	public UniformResourceLocatorBusinessImpl(UniformResourceLocatorDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UniformResourceLocator instanciateOne(String name,String relativeUrl, String[] parameters) {
		UniformResourceLocator uniformResourceLocator = instanciateOne(name);
		uniformResourceLocator.setAddress(relativeUrl);
		for(int i=0;i<parameters.length;i=i+2){
			uniformResourceLocator.addParameter(parameters[i], parameters[i+1]);
		}
		return uniformResourceLocator;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UniformResourceLocator instanciateOne(String relativeUrl, String[] parameters) {
		String[] p = StringUtils.split(relativeUrl,Constant.CHARACTER_SLASH.toString());
		StringBuilder stringBuilder = new StringBuilder(StringUtils.split(p[p.length-1],Constant.CHARACTER_DOT.toString())[0]);
		for(int i=0;i<parameters.length;i=i+2){
			stringBuilder.append(Constant.CHARACTER_SLASH+parameters[i+1]);
		}
		return instanciateOne(stringBuilder.toString(), relativeUrl, parameters);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UniformResourceLocator instanciateOneCrudOne(Class<? extends AbstractIdentifiable> identifiableClass,Crud crud,String[] parameters) {
		UniformResourceLocator uniformResourceLocator = instanciateOne(DYNAMIC_CRUD_ONE, ArrayUtils.addAll(new String[]{UniformResourceLocatorParameter.CLASS
				,RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfos(identifiableClass).getIdentifier()
				,UniformResourceLocatorParameter.CRUD,UniformResourceLocatorParameterBusinessImpl.getCrudAsString(crud)},parameters));
		//System.out.println("Code = <<"+uniformResourceLocator.getCode()+">>");
		return uniformResourceLocator;
	}
	
	@Override
	public UniformResourceLocator instanciateOneCrudMany(Class<? extends AbstractIdentifiable> identifiableClass,String[] parameters) {
		return instanciateOne(DYNAMIC_CRUD_MANY, ArrayUtils.addAll(new String[]{UniformResourceLocatorParameter.CLASS
				,RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfos(identifiableClass).getIdentifier()},parameters));
	}
	
	@Override
	public Collection<UniformResourceLocator> instanciateManyCrud(Class<? extends AbstractIdentifiable> identifiableClass) {
		Collection<UniformResourceLocator> uniformResourceLocators = new ArrayList<>();
		uniformResourceLocators.add(instanciateOneCrudMany(identifiableClass, new String[]{}));
		uniformResourceLocators.add(instanciateOneCrudOne(identifiableClass,null, new String[]{}));
		return uniformResourceLocators;
	}
	
	@Override
	public UniformResourceLocator instanciateOneSelectOne(Class<? extends AbstractIdentifiable> identifiableClass,String actionIdentifier,String[] parameters) {
		return instanciateOne(DYNAMIC_SELECT_ONE, ArrayUtils.addAll(new String[]{UniformResourceLocatorParameter.CLASS
				,RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfos(identifiableClass).getIdentifier()
				,UniformResourceLocatorParameter.ACTION_IDENTIFIER,actionIdentifier},parameters));
	}
	
	@Override
	public UniformResourceLocator instanciateOneSelectMany(Class<? extends AbstractIdentifiable> identifiableClass,String actionIdentifier,String[] parameters) {
		return instanciateOne(DYNAMIC_SELECT_MANY, ArrayUtils.addAll(new String[]{UniformResourceLocatorParameter.CLASS
				,RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfos(identifiableClass).getIdentifier()
				,UniformResourceLocatorParameter.ACTION_IDENTIFIER,actionIdentifier},parameters));
	}
	
	@Override
	public Collection<UniformResourceLocator> instanciateManyBusinessCrud(Class<? extends AbstractIdentifiable> identifiableClass, Boolean list,Boolean edit,Boolean consult,Boolean createMany, String[] selectOneActionIdentifiers,String[] selectManyActionIdentifiers) {
		Collection<UniformResourceLocator> uniformResourceLocators = new ArrayList<>();
		String name = identifiableClass.getSimpleName().toLowerCase();
		String folder = Constant.CHARACTER_SLASH+PRIVATE_FOLDER+Constant.CHARACTER_SLASH+name+Constant.CHARACTER_SLASH;
		
		if(Boolean.TRUE.equals(list))
			uniformResourceLocators.add(instanciateOne(name+Constant.CHARACTER_UNDESCORE+FILE_LIST,folder+FILE_LIST+Constant.CHARACTER_DOT+PROCESSED_FILE_EXTENSION,new String[]{}));
		if(Boolean.TRUE.equals(edit))
			uniformResourceLocators.add(instanciateOne(name+Constant.CHARACTER_UNDESCORE+FILE_EDIT,folder+FILE_EDIT+Constant.CHARACTER_DOT+PROCESSED_FILE_EXTENSION,new String[]{}));
		if(Boolean.TRUE.equals(consult))
			uniformResourceLocators.add(instanciateOne(name+Constant.CHARACTER_UNDESCORE+FILE_CONSULT,folder+FILE_CONSULT+Constant.CHARACTER_DOT+PROCESSED_FILE_EXTENSION,new String[]{}));
		if(Boolean.TRUE.equals(createMany))
			uniformResourceLocators.add(instanciateOne(name+Constant.CHARACTER_UNDESCORE+FILE_CREATE_MANY,folder+FILE_CREATE_MANY+Constant.CHARACTER_DOT+PROCESSED_FILE_EXTENSION,new String[]{}));
		if(selectOneActionIdentifiers!=null)
			for(String selectOneActionIdentifier : selectOneActionIdentifiers)
				uniformResourceLocators.add(instanciateOneSelectOne(identifiableClass, selectOneActionIdentifier, null));
		
		if(selectManyActionIdentifiers!=null)
			for(String selectManyActionIdentifier : selectManyActionIdentifiers)
				uniformResourceLocators.add(instanciateOneSelectMany(identifiableClass, selectManyActionIdentifier, null));
		return uniformResourceLocators;
	}
	
	@Override
	public UniformResourceLocator create(UniformResourceLocator uniformResourceLocator) {
		uniformResourceLocator = super.create(uniformResourceLocator);
		save(uniformResourceLocator);
		return uniformResourceLocator;
	}
	
	private void save(UniformResourceLocator uniformResourceLocator){
		for(UniformResourceLocatorParameter uniformResourceLocatorParameter : uniformResourceLocator.getParameters()){
			uniformResourceLocatorParameter.setUniformResourceLocator(uniformResourceLocator);
			exceptionUtils().exception(uniformResourceLocatorParameter.getName()==null, "no name set");
			if(uniformResourceLocatorParameter.getIdentifier()==null)
				uniformResourceLocatorParameterDao.create(uniformResourceLocatorParameter);
			else
				uniformResourceLocatorParameterDao.update(uniformResourceLocatorParameter);		
		}
	}
	
	@Override
	public UniformResourceLocator save(UniformResourceLocator uniformResourceLocator,Collection<UniformResourceLocatorParameter> parameters) {
		uniformResourceLocator = dao.update(uniformResourceLocator);
		uniformResourceLocator.setParameters(parameters);
		
		Collection<UniformResourceLocatorParameter> database = uniformResourceLocatorParameterDao.readByUniformResourceLocator(uniformResourceLocator);
		
		delete(UniformResourceLocatorParameter.class,uniformResourceLocatorParameterDao,database, uniformResourceLocator.getParameters());
		
		save(uniformResourceLocator);
		return uniformResourceLocator;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public UniformResourceLocator find(URL url,Collection<UniformResourceLocator> uniformResourceLocators) {
		//logTrace("Database URL : {}",uniformResourceLocators);
		logTrace("URL Infos : {} , Path : {} , Query : {}", url,url.getPath(),url.getQuery());
		if(uniformResourceLocators==null || uniformResourceLocators.isEmpty()){
			return null;
		}
		for(UniformResourceLocator uniformResourceLocator : uniformResourceLocators){
			uniformResourceLocator.setParameters(uniformResourceLocatorParameterDao.readByUniformResourceLocator(uniformResourceLocator));
			logTrace("Uniform Resource Locator : {} parameters : {}", uniformResourceLocator,uniformResourceLocator.getParameters());
			//if(StringUtils.startsWith(url.getPath(),uniformResourceLocator.getPath())){
				if(StringUtils.equalsIgnoreCase(url.getPath(),findPath(uniformResourceLocator))){
					logTrace("Matchs path");
					if(uniformResourceLocator.getParameters().isEmpty()){
						logTrace("No parameters to check");
						return uniformResourceLocator;
					}
					Collection<UniformResourceLocatorParameter> urlParameters = new ArrayList<>();
					if(StringUtils.isNotBlank(url.getQuery()))
						for(String query : StringUtils.split(url.getQuery(),'&')){
							String[] p = StringUtils.split(query,"=");
							urlParameters.add(new UniformResourceLocatorParameter(null, p[0], p[1]));
						}
					
					Integer count = 0,size=0;
					for(UniformResourceLocatorParameter parameter : uniformResourceLocator.getParameters()){
						//if(parameter.getValue()!=null)
							size++;
						for(UniformResourceLocatorParameter urlParameter : urlParameters){
							if(parameter.getName().equalsIgnoreCase(urlParameter.getName()) && (parameter.getValue()==null || parameter.getValue().equalsIgnoreCase(urlParameter.getValue()))){
								count++;
								logTrace("Parameter are equals : URL={} , JavaURL={}",parameter,urlParameter);
							}
						}
					}
					
					Boolean match = size == count;
					logTrace("Try to match query parameters whith {}. size={}, count={}, match={}",uniformResourceLocator.getParameters(),size,count,match);
					if(Boolean.TRUE.equals(match))
						return uniformResourceLocator;
				//}else
				//	return uniformResourceLocator;
			}
		}
		return null;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public UniformResourceLocator find(URL url) {
		return find(url,dao.readAll());
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public String findPath(UniformResourceLocator uniformResourceLocator) {
		try {
			if(StringUtils.startsWith(uniformResourceLocator.getAddress().toLowerCase(), "http://"))
				return new URL(uniformResourceLocator.getAddress()).getPath();
			else
				return Constant.CHARACTER_SLASH+RootBusinessLayer.getInstance().getApplicationBusiness().findCurrentInstance()
						.getWebContext()+uniformResourceLocator.getAddress();
		} catch (MalformedURLException e) {
			throw new BusinessExceptionNoRollBack(e.toString());
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isAccessible(URL url,Collection<UniformResourceLocator> uniformResourceLocators) {
		return Boolean.TRUE.equals(RootBusinessLayer.getInstance().getApplicationBusiness().findCurrentInstance().getUniformResourceLocatorFilteringEnabled())
				?find(url,uniformResourceLocators)!=null:Boolean.TRUE;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isAccessible(URL url) {
		return Boolean.TRUE.equals(RootBusinessLayer.getInstance().getApplicationBusiness().findCurrentInstance().getUniformResourceLocatorFilteringEnabled())?find(url)!=null:Boolean.TRUE;
	}

}
