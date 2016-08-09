package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.persistence.api.geography.ElectronicMailDao;

public class ElectronicMailBusinessImpl extends AbstractContactBusinessImpl<ElectronicMail, ElectronicMailDao> implements ElectronicMailBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ElectronicMailBusinessImpl(ElectronicMailDao dao) {
		super(dao); 
	}

}
