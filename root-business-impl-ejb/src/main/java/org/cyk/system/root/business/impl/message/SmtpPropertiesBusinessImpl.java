package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.message.SmtpPropertiesBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.message.SmtpSocketFactory;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;

public class SmtpPropertiesBusinessImpl extends AbstractTypedBusinessService<SmtpProperties, SmtpPropertiesDao> implements SmtpPropertiesBusiness,Serializable {

	private static final long serialVersionUID = 8464902693612253709L;

	@Inject
	public SmtpPropertiesBusinessImpl(SmtpPropertiesDao dao) {
		super(dao);
	}
	
	@Override
	public SmtpProperties instanciateOne(String[] values) {
		SmtpProperties smtpProperties = instanciateOne();
		Integer index = 0;
		smtpProperties.setCode(values[index++]);
		smtpProperties.setHost(values[index++]);
    	smtpProperties.setPort(new BigDecimal(values[index++]).intValue());
		smtpProperties.setFrom(values[index++]);
    	smtpProperties.setCredentials(new Credentials(values[index++],values[index++] ));

		smtpProperties.setSocketFactory(new SmtpSocketFactory());
		smtpProperties.getSocketFactory().setClazz(values[index++]);
		smtpProperties.getSocketFactory().setFallback(Boolean.parseBoolean(values[index++]));
		smtpProperties.getSocketFactory().setPort(new BigDecimal(values[index++]).intValue());
		smtpProperties.setAuthenticated(StringUtils.isNotBlank(smtpProperties.getCredentials().getPassword()));
		return smtpProperties;
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
