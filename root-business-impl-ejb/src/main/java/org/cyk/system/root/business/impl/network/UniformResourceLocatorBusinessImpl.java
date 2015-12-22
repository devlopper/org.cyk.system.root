package org.cyk.system.root.business.impl.network;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessExceptionNoRollBack;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.persistence.api.network.UniformResourceLocatorDao;

public class UniformResourceLocatorBusinessImpl extends AbstractEnumerationBusinessImpl<UniformResourceLocator, UniformResourceLocatorDao> implements UniformResourceLocatorBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	 
	@Inject
	public UniformResourceLocatorBusinessImpl(UniformResourceLocatorDao dao) {
		super(dao); 
	}
	/*
	@Override
	public UniformResourceLocator create(UniformResourceLocator uniformResourceLocator) {
		URLS.add(uniformResourceLocator);
		return uniformResourceLocator;
	}*/
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public UniformResourceLocator find(URL url,Collection<UniformResourceLocator> uniformResourceLocators) {
		//logTrace("Database URL : {}",uniformResourceLocators);
		logTrace("URL Infos : {} , Path : {} , Query : {}", url,url.getPath(),url.getQuery());
		if(uniformResourceLocators==null || uniformResourceLocators.isEmpty()){
			return null;
		}
		for(UniformResourceLocator uniformResourceLocator : uniformResourceLocators){
			logTrace("Uniform Resource Locator : {} parameters : {}", uniformResourceLocator,uniformResourceLocator.getParameters());
			//if(StringUtils.startsWith(url.getPath(),uniformResourceLocator.getPath())){
				if(StringUtils.equalsIgnoreCase(url.getPath(),uniformResourceLocator.getAddress())){
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
				return uniformResourceLocator.getAddress();
		} catch (MalformedURLException e) {
			throw new BusinessExceptionNoRollBack(e.toString());
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isAccessible(URL url,Collection<UniformResourceLocator> uniformResourceLocators) {
		return Boolean.TRUE.equals(RootBusinessLayer.getInstance().getApplication().getUniformResourceLocatorFilteringEnabled())
				?find(url,uniformResourceLocators)!=null:Boolean.TRUE;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean isAccessible(URL url) {
		return Boolean.TRUE.equals(RootBusinessLayer.getInstance().getApplication().getUniformResourceLocatorFilteringEnabled())?find(url)!=null:Boolean.TRUE;
	}

}
