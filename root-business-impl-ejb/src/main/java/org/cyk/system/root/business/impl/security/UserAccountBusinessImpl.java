package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;
import org.cyk.system.root.persistence.api.security.UserAccountDao;

@Stateless
public class UserAccountBusinessImpl extends AbstractTypedBusinessService<UserAccount, UserAccountDao> implements UserAccountBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	private static final Map<String,UserAccount> USER_ACCOUNT_MAP = new HashMap<>();
	
	@Inject private LanguageBusiness languageBusiness;
	//@Inject private RootValueValidator rootValueValidator;
	
	@Inject
	public UserAccountBusinessImpl(UserAccountDao dao) {
		super(dao); 
	}  

	@Override
	public UserAccount create(UserAccount userAccount) {
		userAccount.setCreationDate(universalTimeCoordinated());
		//Use validator and externalise rules to file
		return super.create(userAccount); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public UserAccount findByCredentials(Credentials credentials) {
		return dao.readByCredentials(credentials);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public UserAccount findByUsername(String username) {
		return dao.readByUsername(username);
	}

	@Override
	public UserAccount connect(Credentials credentials) {
		UserAccount account = findByCredentials(credentials);
		if(account==null){	
			exceptionUtils().exception(account==null, "exception.useraccount.unknown");
		}else{
			//TODO
			//exceptionUtils().exception(USER_ACCOUNT_MAP.get(credentials.getUsername())!=null,EXCEPTION_ALREADY_CONNECTED, "exception.useraccount.multipleconnect");
		}
		
		USER_ACCOUNT_MAP.put(credentials.getUsername(),account);
		return account;
	}

	@Override
	public void disconnect(UserAccount userAccount) {
		userAccount.getSessionNotifications().clear();
		USER_ACCOUNT_MAP.remove(userAccount.getCredentials().getUsername());
	}
	
	/*
	@Override
	public void updatePassword(UserAccount userAccount,String oldPassword, String newPassword) {
		exceptionUtils().exception(userAccount.getCredentials().getPassword().equals(oldPassword), "exception.useraccount.update.incorrectoldpassword");
		
		userAccount.getCredentials().setPassword(newPassword);
		credentialsDao.update(userAccount.getCredentials());
	}*/
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public String findStatus(UserAccount userAccount) {
		if(userAccount.getCurrentLock()==null)
			return languageBusiness.findText("enabled");
		return languageBusiness.findText("locked");
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<UserAccount> findByCriteria(UserAccountSearchCriteria criteria) {
		Collection<UserAccount> userAccounts;
		if(StringUtils.isBlank(criteria.getUsernameSearchCriteria().getPreparedValue()))
			userAccounts = dao.readAllExcludeRoles(Arrays.asList(RootBusinessLayer.getInstance().getAdministratorRole()));
		else
			userAccounts = dao.readByCriteria(criteria);
		for(UserAccount userAccount : userAccounts)
			userAccount.setStatus(findStatus(userAccount));
		return userAccounts;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(UserAccountSearchCriteria criteria) {
		if(StringUtils.isBlank(criteria.getUsernameSearchCriteria().getPreparedValue()))
			return dao.countAllExcludeRoles(Arrays.asList(RootBusinessLayer.getInstance().getAdministratorRole()));
		return dao.countByCriteria(criteria);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean createSessionNotification(UserAccount userAccount,Notification notification) {
		if(contains(userAccount.getSessionNotificationsDeleted(), notification) || 
				contains(userAccount.getSessionNotifications(), notification))
			return Boolean.FALSE;
		userAccount.getSessionNotifications().add(notification);
		return Boolean.TRUE;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void deleteSessionNotification(UserAccount userAccount,Notification notification) {
		for(int i = 0;i<userAccount.getSessionNotifications().size();)
			if(equals(userAccount.getSessionNotifications().get(i),notification)){
				userAccount.getSessionNotificationsDeleted().add(userAccount.getSessionNotifications().remove(i));
			}else
				i++;
	}
	
	private Boolean equals(Notification notification1,Notification notification2){
		return notification1.getTitle().equals(notification2.getTitle()) && notification1.getMessage().equals(notification2.getMessage());
	}
	
	private Boolean contains(Collection<Notification> notifications,Notification notification){
		for(Notification n : notifications)
			if(equals(n, notification))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<UserAccount> findAllExcludeRoles(Collection<Role> roles) {
		return dao.readAllExcludeRoles(roles);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countAllExcludeRoles(Collection<Role> roles) {
		return dao.countAllExcludeRoles(roles);
	}

	
}
