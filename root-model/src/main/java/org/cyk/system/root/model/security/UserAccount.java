package org.cyk.system.root.model.security;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;

@Getter @Setter @Entity
public class UserAccount extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -23914558440705885L;

	@ManyToOne private Party user;
	
	@ManyToOne private Credentials credentials = new Credentials();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToMany
    @JoinTable(name="UserRoles",joinColumns = { @JoinColumn(name = "userId") } ,inverseJoinColumns={ @JoinColumn(name = "role") })
    private Set<Role> roles =new HashSet<>();
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Collection<SecretQuestionAnswer> secretQuestionAnswers = new LinkedHashSet<>();
	
}
