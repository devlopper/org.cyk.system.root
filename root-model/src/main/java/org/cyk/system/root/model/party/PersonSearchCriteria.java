package org.cyk.system.root.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.search.StringSearchCriteria;

@Getter @Setter
public class PersonSearchCriteria extends PartySearchCriteria implements Serializable {

	private static final long serialVersionUID = 6796076474234170332L;

	protected StringSearchCriteria lastNameSearchCriteria = new StringSearchCriteria();
	
	public PersonSearchCriteria(){
		this(null);
	}
	
	public PersonSearchCriteria(String name) {
		super(name);
		lastNameSearchCriteria.setValue(name);
		criterias.add(lastNameSearchCriteria);
		
	}
	
}
