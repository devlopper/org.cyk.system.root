package org.cyk.system.root.model.party;

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
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.annotation.UIFieldOrder;
import org.cyk.utility.common.annotation.UIFieldOrders;
import org.cyk.utility.common.annotation.UIField.OneRelationshipInputType;

@Getter @Setter 
@Entity
@UIFieldOrders(values={
        @UIFieldOrder(fieldName="contactCollection",underFieldName="nationality")
})
public class Person  extends Party  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@UIField
	private String lastName;
	
	@UIField
	@Temporal(TemporalType.DATE) private Date birthDate;
	
	@UIField(oneRelationshipInputType=OneRelationshipInputType.FORM)
	@Valid
    @OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) private Location birthLocation;// = new Location();
	
	@UIField
	@ManyToOne private Sex sex;
	
	@UIField
	@ManyToOne private MaritalStatus maritalStatus;
	
	@UIField
	@ManyToOne private Locality nationality;
	
	//TODO info to add : Job (Profession,Function)
	
	public String getNames(){
		return name+(StringUtils.isEmpty(lastName)?"":(" "+lastName));
	}




}