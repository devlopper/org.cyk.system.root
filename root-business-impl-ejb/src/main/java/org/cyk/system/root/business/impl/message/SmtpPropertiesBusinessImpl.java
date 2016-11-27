package org.cyk.system.root.business.impl.message;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.message.SmtpPropertiesBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;

public class SmtpPropertiesBusinessImpl extends AbstractTypedBusinessService<SmtpProperties, SmtpPropertiesDao> implements SmtpPropertiesBusiness,Serializable {

	private static final long serialVersionUID = 8464902693612253709L;

	@Inject
	public SmtpPropertiesBusinessImpl(SmtpPropertiesDao dao) {
		super(dao);
	}
	
	@Override
	public SmtpProperties create(SmtpProperties smtpProperties) {
		if(isNotIdentified(smtpProperties.getCredentials()))
			genericDao.create(smtpProperties.getCredentials());
		return super.create(smtpProperties);
	}
	
	@Override
	public SmtpProperties update(SmtpProperties smtpProperties) {
		if(isIdentified(smtpProperties.getCredentials()))
			genericDao.update(smtpProperties.getCredentials());
		else if(isNotIdentified(smtpProperties.getCredentials()))
			genericDao.create(smtpProperties.getCredentials());
		return super.update(smtpProperties);
	}

}
