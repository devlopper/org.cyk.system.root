package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;

@Getter @Setter @Entity @NoArgsConstructor
public class UserAccount extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -23914558440705885L;

	@ManyToOne private Party user;
	
	@OneToOne(cascade=CascadeType.ALL) private Credentials credentials = new Credentials();
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull @Column(nullable=false)
	private Date creationDate;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="UserAccountRoles",joinColumns = { @JoinColumn(name = "useraccountid") } ,inverseJoinColumns={ @JoinColumn(name = "roleid") })
	@Size(min=1)
    private Set<Role> roles =new HashSet<>();
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Collection<SecretQuestionAnswer> secretQuestionAnswers = new LinkedHashSet<>();

	@OneToOne private UserAccountLock currentLock;
	
	/**/
	
	@Transient private String status;
	
	/**/
	
	public UserAccount(Party user, Credentials credentials,Date creationDate,Role...roles) {
		super();
		this.user = user;
		this.credentials = credentials;
		this.creationDate = creationDate;
		if(roles!=null)
			this.roles.addAll(Arrays.asList(roles));
	}
	
	@Override
	public String toString() {
		return super.toString()+","+user.getContactCollection().getIdentifier();
	}
}
