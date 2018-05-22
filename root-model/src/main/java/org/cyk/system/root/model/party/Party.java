package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FilterHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Entity @NoArgsConstructor @Inheritance(strategy=InheritanceType.JOINED) @Accessors(chain=true)
/**
 * A moral or physical person
 * @author Christian
 *
 */
public class Party extends AbstractIdentifiable  implements Serializable{
	private static final long serialVersionUID = 1L;
	 
	@OneToOne @JoinColumn(name=COLUMN_CONTACT_COLLECTION) protected ContactCollection contactCollection;
	
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

	public static final String COLUMN_CONTACT_COLLECTION = FIELD_CONTACT_COLLECTION;
	
	public static final String VARIABLE_NAME = ClassHelper.getInstance().getVariableName(Party.class);
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
		public FilterHelper.Filter<PARTY> use(String string) {
			contactCollection.use(string);
			return super.use(string);
		}
		
	}

	
}