package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;

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
	
	public void addContact(Contact contact) {
		if(contactCollection==null)
			contactCollection = new ContactCollection();
		contactCollection.add(contact);
	}
	
	@Override
	public String getUiString() {
		return getGlobalIdentifier().getName();
	}

	/**/
	
	@Getter @Setter
	public static class PartySearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected StringSearchCriteria name = new StringSearchCriteria();
		
		public PartySearchCriteria(){
			this(null);
		}
		
		public PartySearchCriteria(String name) {
			setStringSearchCriteria(this.name, name);
			
		}
		
	}

	
}