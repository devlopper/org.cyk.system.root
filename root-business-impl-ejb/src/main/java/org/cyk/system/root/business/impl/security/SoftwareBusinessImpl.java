package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.security.SoftwareBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.persistence.api.security.SoftwareDao;

public class SoftwareBusinessImpl extends AbstractEnumerationBusinessImpl<Software, SoftwareDao> implements SoftwareBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public SoftwareBusinessImpl(SoftwareDao dao) {
		super(dao); 
	}
	
}
