package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.persistence.api.geography.PhoneNumberDao;

public class PhoneNumberBusinessImpl extends AbstractContactBusinessImpl<PhoneNumber, PhoneNumberDao> implements PhoneNumberBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PhoneNumberBusinessImpl(PhoneNumberDao dao) {
		super(dao); 
	}
	
}
