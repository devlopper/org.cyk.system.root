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
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.annotation.UIFieldOrder;
import org.cyk.utility.common.annotation.UIFieldOrders;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter 
@Entity
@UIFieldOrders(values={
        @UIFieldOrder(fieldName="contactCollection",underFieldName="nationality")
})
@NoArgsConstructor
public class Person  extends Party  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Input
	@InputText
	private String lastName;
	
	@Input
	@InputCalendar
	@Temporal(TemporalType.DATE) private Date birthDate;
	
	@Valid
    @OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) private Location birthLocation;
	
	@Input
	@InputChoice
	@InputOneChoice
	@InputOneCombo
	@ManyToOne private Sex sex;
	
	@Input
	@InputChoice
	@InputOneChoice
	@InputOneCombo
	@ManyToOne private MaritalStatus maritalStatus;
	
	@Input
	@InputChoice
	@InputOneChoice
	@InputOneCombo
	@ManyToOne private Locality nationality;
	
	@Input
	@InputChoice
	@InputOneChoice
	@InputOneCombo
	@OneToOne(cascade=CascadeType.ALL) private PersonCredentials credentials;
	
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