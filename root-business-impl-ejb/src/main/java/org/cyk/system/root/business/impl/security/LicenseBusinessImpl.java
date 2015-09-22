package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.persistence.api.security.LicenseDao;

@Stateless
public class LicenseBusinessImpl extends AbstractTypedBusinessService<License, LicenseDao> implements LicenseBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	private static Boolean ENABLED = Boolean.FALSE;
	
	@Inject
	public LicenseBusinessImpl(LicenseDao dao) {
		super(dao); 
	}
	
	@Override
	public void expire(License license) {
		license.setExpired(Boolean.TRUE);
		update(license);
	}

	@Override
	public void expand(License license) {
		license.setExpired(Boolean.FALSE);
		update(license);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean getEnabled() {
		return ENABLED;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void setEnabled(Boolean value) {
		ENABLED = value;
	}  
		
}
