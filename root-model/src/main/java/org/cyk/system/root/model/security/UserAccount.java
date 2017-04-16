package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) 
public class UserAccount extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -23914558440705885L;

	@ManyToOne @NotNull private Party user;
	
	@OneToOne private Credentials credentials;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="UserAccountRoles",joinColumns = { @JoinColumn(name = "useraccountid") } ,inverseJoinColumns={ @JoinColumn(name = "roleid") })
	@Size(min=1)
    private Set<Role> roles =new HashSet<>();

	//private Boolean disabled;
	
	/**/
	
	@Transient private UserAccountLock currentLock;
	@Transient private String status;
	@Transient private final List<Notification> sessionNotifications = new ArrayList<>();
	@Transient private final List<Notification> sessionNotificationsDeleted = new ArrayList<>();
	@Transient private Integer connectionAttemptCount = 0;
	
	/**/
	
	public UserAccount(Party user, Credentials credentials,Date creationDate,Role...roles) {
		super();
		this.user = user;
		this.credentials = credentials;
		setBirthDate(creationDate);
		if(roles!=null)
			this.roles.addAll(Arrays.asList(roles));
	}
	
	@Override
	public String getUiString() {
		return user.getUiString()+Constant.CHARACTER_VERTICAL_BAR+credentials.getUsername();
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_CREDENTIALS = "credentials";
	public static final String FIELD_ROLES = "roles";
	
	
	@Override
	public String toString() {
		return getUiString();
	}
	
	/**/
	
	@Getter @Setter
	public class SearchCriteria extends AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 3134811510557411588L;

		//private DateSearchCriteria creationDateSearchCriteria;
		private StringSearchCriteria usernameSearchCriteria;
		//private Collection<UserAccount> userAccountExcluded = new ArrayList<>();
		//private Collection<Role> roleExcluded = new ArrayList<>();
		
		public SearchCriteria(String username){
			super(username);
			usernameSearchCriteria = new StringSearchCriteria();
			setStringSearchCriteria(usernameSearchCriteria, username);
		}
		
		
	}
}
