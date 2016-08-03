package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.SexBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.persistence.api.party.person.SexDao;

public class SexBusinessImpl extends AbstractEnumerationBusinessImpl<Sex, SexDao> implements SexBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public SexBusinessImpl(SexDao dao) {
		super(dao); 
	}   
	
}
