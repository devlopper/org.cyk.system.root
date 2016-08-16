package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.MaritalStatusBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.persistence.api.party.person.MaritalStatusDao;

public class MaritalStatusBusinessImpl extends AbstractEnumerationBusinessImpl<MaritalStatus, MaritalStatusDao> implements MaritalStatusBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MaritalStatusBusinessImpl(MaritalStatusDao dao) {
		super(dao); 
	}   
	
}
