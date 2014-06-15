package org.cyk.system.root.model.party;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.utility.common.validation.Client;

@Getter @Setter 
@Entity
public class Person  extends Party  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String lastName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(groups=Client.class)
	private Date birthDate;
	
	@Enumerated(EnumType.ORDINAL)
	@NotNull(groups=Client.class)
	@Column(nullable=false)
	private Sexe sex;
	
	@ManyToOne
	private MaritalStatus maritalStatus;
	
	@ManyToOne
	@NotNull(groups=Client.class)
	private Locality nationality;
	
	public String getNames(){
		return firstName+(StringUtils.isEmpty(lastName)?"":(" "+lastName));
	}




}