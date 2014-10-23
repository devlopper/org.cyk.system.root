package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
	private Date creationDate;
	
	@ManyToMany
    @JoinTable(name="UserAccountRoles",joinColumns = { @JoinColumn(name = "useraccountid") } ,inverseJoinColumns={ @JoinColumn(name = "roleid") })
    private Set<Role> roles =new HashSet<>();
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Collection<SecretQuestionAnswer> secretQuestionAnswers = new LinkedHashSet<>();

	public UserAccount(Party user, Credentials credentials,Role...roles) {
		super();
		this.user = user;
		this.credentials = credentials;
		if(roles!=null)
			this.roles.addAll(Arrays.asList(roles));
	}
	
	
}
