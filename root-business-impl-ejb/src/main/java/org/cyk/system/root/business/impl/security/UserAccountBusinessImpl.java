package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.UserSessionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;
import org.cyk.system.root.persistence.api.security.RoleDao;
import org.cyk.system.root.persistence.api.security.UserAccountDao;
import org.cyk.utility.common.Constant;

@Stateless //@Secure
public class UserAccountBusinessImpl extends AbstractTypedBusinessService<UserAccount, UserAccountDao> implements UserAccountBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	private static final Map<String,UserAccount> USER_ACCOUNT_MAP = new HashMap<>();
	
	@Inject private LanguageBusiness languageBusiness;
	@Inject private UserSessionBusiness userSessionBusiness;
	
	@Inject private RoleDao roleDao;
	
	@Inject
	public UserAccountBusinessImpl(UserAccountDao dao) {
		super(dao); 
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
			logInfo("User account connected : Username={} , Roles={}", account.getCredentials().getUsername(),account.getRoles());
		}
		userSessionBusiness.setUserAccount(account);
		
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
			userAccounts = dao.readAllExcludeRoles(Arrays.asList(inject(RoleBusiness.class).find(Role.ADMINISTRATOR)));
		else
			userAccounts = dao.readByCriteria(criteria);
		for(UserAccount userAccount : userAccounts)
			userAccount.setStatus(findStatus(userAccount));
		return userAccounts;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER) //@Secure 
	public Long countByCriteria(UserAccountSearchCriteria criteria) {
		if(StringUtils.isBlank(criteria.getUsernameSearchCriteria().getPreparedValue()))
			return dao.countAllExcludeRoles(Arrays.asList(inject(RoleBusiness.class).find(Role.ADMINISTRATOR)));
		return dao.countByCriteria(criteria);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean createSessionNotification(UserAccount userAccount,Notification notification) {
		if(contains(userAccount.getSessionNotificationsDeleted(), notification) || 
				contains(userAccount.getSessionNotifications(), notification))
			return Boolean.FALSE;
		userAccount.getSessionNotifications().add(notification);
		return Boolean.TRUE;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean hasAtLeastOneRole(UserAccount userAccount, Collection<Role> roles) {
		for(Role role1 : userAccount.getRoles())
			for(Role role2 : roles)
				if(role1.equals(role2))
					return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean hasRole(UserAccount userAccount, Role role) {
		return hasAtLeastOneRole(userAccount, Arrays.asList(role));
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UserAccount instanciateOne(Party party,Role...roles) {
		UserAccount userAccount = new UserAccount();
		userAccount.setUser(party);
		userAccount.getCredentials().setUsername(StringUtils.replace(party.getGlobalIdentifier().getName(), Constant.CHARACTER_SPACE.toString(), Constant.EMPTY_STRING.toString()));
		userAccount.getCredentials().setPassword(RandomStringUtils.randomAlphanumeric(6));
		if(roles!=null)
			userAccount.getRoles().addAll(Arrays.asList(roles));
		return userAccount;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<UserAccount> instanciateManyFromParties(Collection<Party> parties, Role... roles) {
		Collection<UserAccount> userAccounts = new ArrayList<>();
		for(Party party : parties)
			userAccounts.add(instanciateOne(party, roles));
		return userAccounts;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<UserAccount> instanciateManyFromActors(Collection<? extends AbstractActor> actors,Role... roles) {
		Collection<Party> parties = new ArrayList<>();
		for(AbstractActor actor : actors)
			parties.add(actor.getPerson());
		return instanciateManyFromParties(parties, roles);
	}

	@Override
	public Boolean canCreate(UserAccount userAccount,Class<? extends AbstractIdentifiable> aClass) {
		return Boolean.TRUE;
	}

	@Override
	public Boolean canRead(UserAccount userAccount,AbstractIdentifiable identifiable) {
		return hasRole(userAccount, roleDao.readByGlobalIdentifierCode(Role.ADMINISTRATOR)) || inject(GlobalIdentifierBusiness.class).isReadable(identifiable);
	}

	@Override
	public Boolean canUpdate(UserAccount userAccount,AbstractIdentifiable identifiable) {
		return hasRole(userAccount, roleDao.readByGlobalIdentifierCode(Role.ADMINISTRATOR)) || inject(GlobalIdentifierBusiness.class).isUpdatable(identifiable);
	}

	@Override
	public Boolean canDelete(UserAccount userAccount,AbstractIdentifiable identifiable) {
		return hasRole(userAccount, roleDao.readByGlobalIdentifierCode(Role.ADMINISTRATOR)) || inject(GlobalIdentifierBusiness.class).isDeletable(identifiable);
	}
}
