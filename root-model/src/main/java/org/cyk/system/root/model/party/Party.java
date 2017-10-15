package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.helper.FilterHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity @NoArgsConstructor @Inheritance(strategy=InheritanceType.JOINED)
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) FIXME there is an issue : entity listener method are not called
public class Party extends AbstractIdentifiable  implements Serializable{

	private static final long serialVersionUID = 1L;
	 
	@OneToOne protected ContactCollection contactCollection;
	
	public Party(String name) {
		super();
		setName(name);
	}
	
	public Party addContact(Contact contact) {
		if(contactCollection==null)
			contactCollection = new ContactCollection();
		contactCollection.add(contact);
		return this;
	}
	
	public Party addElectronicMail(String address){
		if(contactCollection==null)
			contactCollection = new ContactCollection();
		contactCollection.add(new ElectronicMailAddress(address));
		return this;
	}
	
	@Override
	public String getUiString() {
		return getGlobalIdentifier().getName();
	}
	
	/**/
	
	public static final String FIELD_CONTACT_COLLECTION = "contactCollection";

	/**/
	
	@Getter @Setter
	public static class PartySearchCriteria extends AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected ContactCollection.SearchCriteria contactCollection = new ContactCollection.SearchCriteria();
		
		public PartySearchCriteria(){ 
			this(null);
		}
		
		public PartySearchCriteria(String name) {
			super(name);
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			super.set(stringSearchCriteria);
			contactCollection.set(stringSearchCriteria);
		}
		
		@Override
		public void set(String value) {
			super.set(value);
			contactCollection.set(value);
		}

	}
	
	@Getter @Setter
	public static class Filter<PARTY extends Party> extends AbstractIdentifiable.Filter<PARTY> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;

		protected ContactCollection.Filter contactCollection = new ContactCollection.Filter();
		
		public Filter() {
			addCriterias(contactCollection);
		}
		
		@Override
		public FilterHelper.Filter<PARTY> set(String string) {
			contactCollection.set(string);
			return super.set(string);
		}
		
	}

	
}