package org.cyk.system.root.model.party.person;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.event.RepeatedEvent;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter  @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class Person  extends Party  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String lastName;
	
	@Temporal(TemporalType.DATE) private Date birthDate;
	
	@Valid @OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) private Location birthLocation;
	
	@ManyToOne private Sex sex;
	
	@ManyToOne private MaritalStatus maritalStatus;
	
	@ManyToOne private Locality nationality;
	
	@OneToOne(cascade=CascadeType.ALL) private PersonCredentials credentials;
	
	@OneToOne(cascade=CascadeType.ALL) private RepeatedEvent birthDateAnniversary;
	
	//TODO info to add : Job (Profession,Function)
	
	public Person(String firstName,String lastName) {
		super(firstName);
		this.lastName = lastName;
	}
	
	public String getNames(){
		return name+(StringUtils.isEmpty(lastName)?"":(" "+lastName));
	}

	@Override
	public String toString() {
		return getNames();
	}

	@Override
	public String getUiString() {
		return getNames();
	}



}