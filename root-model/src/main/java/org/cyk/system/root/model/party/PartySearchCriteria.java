package org.cyk.system.root.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;

@Getter @Setter
public class PartySearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 6796076474234170332L;

	protected StringSearchCriteria nameSearchCriteria = new StringSearchCriteria();
	
	public PartySearchCriteria(){
		this(null);
	}
	
	public PartySearchCriteria(String name) {
		setStringSearchCriteria(nameSearchCriteria, name);
		
	}
	
}
