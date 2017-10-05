package org.cyk.system.root.model.geography;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollection;
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
	
	
	
	public ContactCollection addPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
		add(PhoneNumber.class, phoneNumbers);
		return this;
	}
	
	public ContactCollection addElectronicMailAddresses(Collection<ElectronicMailAddress> electronicMailAddresses) {
		add(ElectronicMailAddress.class, electronicMailAddresses);
		return this;
	}
	
	public ContactCollection addLocations(Collection<Location> locations) {
		add(Location.class, locations);
		return this;
	}
	
	public ContactCollection addPostalBoxs(Collection<PostalBox> postalBoxs) {
		add(PostalBox.class, postalBoxs);
		return this;
	}
	
	public ContactCollection addWebsites(Collection<Website> websites) {
		add(Website.class, websites);
		return this;
	}

	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		private ElectronicMailAddress.SearchCriteria electronicMailAddress = new ElectronicMailAddress.SearchCriteria();
		
		public SearchCriteria(){ 
			this(null);
		}
		
		public SearchCriteria(String name) {
			super(name);
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			super.set(stringSearchCriteria);
			electronicMailAddress.set(stringSearchCriteria);
		}
		
		@Override
		public void set(String value) {
			super.set(value);
			electronicMailAddress.set(value);
		}
		
	}
}
