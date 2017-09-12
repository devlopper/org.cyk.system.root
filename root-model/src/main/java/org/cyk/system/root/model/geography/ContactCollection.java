package org.cyk.system.root.model.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class ContactCollection extends AbstractCollection<Contact> implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
	
	/*
	@Transient private Collection<PhoneNumber> phoneNumbers;
	@Transient private Collection<Location> locations;
	@Transient private Collection<PostalBox> postalBoxs;
	@Transient private Collection<ElectronicMail> electronicMails;
	@Transient private Collection<Website> websites;
	*/

	@Override
	public ContactCollection add(Contact contact) {
		super.add(contact);
		/*if(contact instanceof PhoneNumber)
			addPhoneNumber((PhoneNumber) contact);
		else if(contact instanceof Location)
			addLocation((Location) contact);
		else if(contact instanceof PostalBox)
			addPostalBox((PostalBox) contact);
		else if(contact instanceof ElectronicMail)
			addElectronicMail((ElectronicMail) contact);
		else if(contact instanceof Website)
			addWebsite((Website) contact);
		*/
		return this;
	}
	
	public ContactCollection addPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
		for(PhoneNumber phoneNumber : phoneNumbers)
			add(phoneNumber);
		return this;
	}
	
	public ContactCollection addElectronicMails(Collection<ElectronicMail> electronicMails) {
		for(ElectronicMail electronicMail : electronicMails)
			add(electronicMail);
		return this;
	}
	
	/*
	private ContactCollection addPhoneNumber(PhoneNumber phoneNumber){
		if(phoneNumbers==null)
			phoneNumbers = new ArrayList<>();
		phoneNumbers.add(phoneNumber);
		return this;
	}
	
	private ContactCollection addLocation(Location location){
		if(locations==null)
			locations = new ArrayList<>();
		locations.add(location);
		return this;
	}
	
	private ContactCollection addPostalBox(PostalBox postalBox){
		if(postalBoxs==null)
			postalBoxs = new ArrayList<>();
		postalBoxs.add(postalBox);
		return this;
	}
	
	private ContactCollection addElectronicMail(ElectronicMail electronicMail){
		if(electronicMails==null)
			electronicMails = new ArrayList<>();
		electronicMails.add(electronicMail);
		return this;
	}
	
	private ContactCollection setElectronicMail(String address){
		if(StringUtils.isBlank(address)){
			if(electronicMails!=null)
				electronicMails.clear();
		}else{
			if(electronicMails==null || electronicMails.isEmpty())
				return addElectronicMail(new ElectronicMail(this,address));
			electronicMails.iterator().next().setAddress(address);	
		}
		
		return this;
	}
	
	private ContactCollection addWebsite(Website website){
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
	*/

	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		private ElectronicMail.SearchCriteria electronicMail = new ElectronicMail.SearchCriteria();
		
		public SearchCriteria(){ 
			this(null);
		}
		
		public SearchCriteria(String name) {
			super(name);
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			super.set(stringSearchCriteria);
			electronicMail.set(stringSearchCriteria);
		}
		
		@Override
		public void set(String value) {
			super.set(value);
			electronicMail.set(value);
		}
		
	}
}
