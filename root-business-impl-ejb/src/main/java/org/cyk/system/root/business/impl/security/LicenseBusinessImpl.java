package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.persistence.api.security.LicenseDao;

@Stateless
public class LicenseBusinessImpl extends AbstractTypedBusinessService<License, LicenseDao> implements LicenseBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public LicenseBusinessImpl(LicenseDao dao) {
		super(dao); 
	}
		
}
