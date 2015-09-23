package org.cyk.system.root.business.impl.network;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.network.UniformResourceLocatorDao;

import lombok.Getter;
import lombok.Setter;

@Singleton //@Deployment(initialisationType=InitialisationType.EAGER,order=100)
public class UniformResourceLocatorBusinessImpl extends AbstractEnumerationBusinessImpl<UniformResourceLocator, UniformResourceLocatorDao> implements UniformResourceLocatorBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	//TODO to be moved in database
	private final static Collection<UniformResourceLocator> URLS = new ArrayList<>();
	
	@Getter @Setter private Boolean filteringEnabled = Boolean.FALSE;
	
	@Inject
	public UniformResourceLocatorBusinessImpl(UniformResourceLocatorDao dao) {
		super(dao); 
	}
	
	@Override
	public UniformResourceLocator create(UniformResourceLocator uniformResourceLocator) {
		URLS.add(uniformResourceLocator);
		return uniformResourceLocator;
	}
	
	@Override
	public UniformResourceLocator find(Collection<UniformResourceLocator> uniformResourceLocators,URL url) {
		logTrace("Database URL : {}",uniformResourceLocators);
		logTrace("Java URL : {} , Path : {} , Query : {}", url,url.getPath(),url.getQuery());
		if(uniformResourceLocators==null || uniformResourceLocators.isEmpty()){
			return null;
		}
		for(UniformResourceLocator uniformResourceLocator : uniformResourceLocators){
			logTrace("Uniform Resource Locator : {} parameters : {}", uniformResourceLocator,uniformResourceLocator.getParameters());
			if(StringUtils.startsWith(url.getPath(),uniformResourceLocator.getPath())){
				if(StringUtils.equalsIgnoreCase(url.getPath(),uniformResourceLocator.getPath())){
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
					
					Integer count = 0;
					for(UniformResourceLocatorParameter parameter : uniformResourceLocator.getParameters()){
						for(UniformResourceLocatorParameter urlParameter : urlParameters){
							if(parameter.getName().equalsIgnoreCase(urlParameter.getName()) && (parameter.getValue()==null || parameter.getValue().equalsIgnoreCase(urlParameter.getValue()))){
								count++;
								logTrace("Parameter are equals : URL={} , JavaURL={}",parameter,urlParameter);
							}
						}
					}
					
					Boolean match = uniformResourceLocator.getParameters().size() == count;
					logTrace("Try to match query parameters whith {}. {} found , match={}",uniformResourceLocator.getParameters(),count,match);
					if(Boolean.TRUE.equals(match))
						return uniformResourceLocator;
				}else
					return uniformResourceLocator;
			}
		}
		return null;
	}
	
	@Override
	public UniformResourceLocator find(URL url) {
		return find(URLS, url);
	}
	
	@Override
	public UniformResourceLocator findByRoles(Collection<Role> roles, URL url) {
		Collection<UniformResourceLocator> collection = new ArrayList<>();
		for(Role role : roles)
			collection.addAll(role.getUniformResourceLocators());
		return find(collection, url);
	}

	@Override
	public UniformResourceLocator findByUserAccount(UserAccount userAccount, URL url) {
		return findByRoles(userAccount.getRoles(), url);
	}
	
	@Override
	public Boolean isAccessible(Collection<UniformResourceLocator> uniformResourceLocators, URL url) {
		return Boolean.TRUE.equals(filteringEnabled)?find(uniformResourceLocators, url)!=null:Boolean.TRUE;
	}
	
	@Override
	public Boolean isAccessible(URL url) {
		return Boolean.TRUE.equals(filteringEnabled)?find(url)!=null:Boolean.TRUE;
	}

	@Override
	public Boolean isAccessibleByRoles(Collection<Role> roles, URL url) {
		return Boolean.TRUE.equals(filteringEnabled)?findByRoles(roles, url)!=null:Boolean.TRUE;
	}

	@Override
	public Boolean isAccessibleByUserAccount(UserAccount userAccount, URL url) {
		return Boolean.TRUE.equals(filteringEnabled)?findByUserAccount(userAccount, url)!=null:Boolean.TRUE;
	}
}
