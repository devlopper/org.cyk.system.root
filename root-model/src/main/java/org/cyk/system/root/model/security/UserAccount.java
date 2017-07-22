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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) 
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {UserAccount.FIELD_USER,UserAccount.FIELD_CREDENTIALS})})
public class UserAccount extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -23914558440705885L;

	@ManyToOne @JoinColumn(name=COLUMN_USER) @NotNull private Party user;
	
	@OneToOne @JoinColumn(name=COLUMN_CREDENTIALS) @NotNull private Credentials credentials;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name=TABLE_USER_ACCOUNT_ROLE,joinColumns = { @JoinColumn(name = COLUMN_USER_ACCOUNT) } ,inverseJoinColumns={ @JoinColumn(name = COLUMN_ROLES) })
	@Size(min=1)
    private Set<Role> roles =new HashSet<>();
	
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
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_CREDENTIALS = "credentials";
	public static final String FIELD_ROLES = "roles";
	
	public static final String COLUMN_USER = "user_";
	public static final String COLUMN_CREDENTIALS = "credentials";
	
	public static final String COLUMN_ROLES = "roleid";
	public static final String COLUMN_USER_ACCOUNT = "useraccountid";
	public static final String TABLE_USER_ACCOUNT_ROLE = "UserAccountRoles";

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
