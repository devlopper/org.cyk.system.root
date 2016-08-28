package org.cyk.system.root.model.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.ContentType;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class ContactCollection extends AbstractCollection<Contact> implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
	
	@Transient private Collection<PhoneNumber> phoneNumbers;
	@Transient private Collection<Location> locations;
	@Transient private Collection<PostalBox> postalBoxs;
	@Transient private Collection<ElectronicMail> electronicMails;
	@Transient private Collection<Website> websites;

	@Override
	public Contact add(Contact item) {
		if(item instanceof PhoneNumber)
			addPhoneNumber((PhoneNumber) item);
		else if(item instanceof Location)
			addLocation((Location) item);
		else if(item instanceof PostalBox)
			addPostalBox((PostalBox) item);
		else if(item instanceof ElectronicMail)
			addElectronicMail((ElectronicMail) item);
		else if(item instanceof Website)
			addWebsite((Website) item);
		return super.add(item);
	}
	
	public ContactCollection addPhoneNumber(PhoneNumber phoneNumber){
		if(phoneNumbers==null)
			phoneNumbers = new ArrayList<>();
		phoneNumbers.add(phoneNumber);
		return this;
	}
	
	public ContactCollection addLocation(Location location){
		if(locations==null)
			locations = new ArrayList<>();
		locations.add(location);
		return this;
	}
	
	public ContactCollection addPostalBox(PostalBox postalBox){
		if(postalBoxs==null)
			postalBoxs = new ArrayList<>();
		postalBoxs.add(postalBox);
		return this;
	}
	
	public ContactCollection addElectronicMail(ElectronicMail electronicMail){
		if(electronicMails==null)
			electronicMails = new ArrayList<>();
		electronicMails.add(electronicMail);
		return this;
	}
	
	public ContactCollection addWebsite(Website website){
		if(websites==null)
			websites = new ArrayList<>();
		websites.add(website);
		return this;
	}
	
	@Override
	public String toString() {
	    StringBuilder s = new StringBuilder(super.toString()+ContentType.DEFAULT.getNewLineMarker());
	    s.append("Phone Numbers : "+StringUtils.join(phoneNumbers,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append("Locations : "+StringUtils.join(locations,","));
	    return s.toString();
	}
	/*
	@Override
	public String getUiString() {
		StringBuilder s = new StringBuilder(super.toString()+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(phoneNumbers,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(electronicMails,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(locations,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(postalBoxs,",")+ContentType.DEFAULT.getNewLineMarker());
	    s.append(StringUtils.join(websites,","));
	    return s.toString();
	}*/
	
}
