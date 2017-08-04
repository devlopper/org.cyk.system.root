package org.cyk.system.root.business.impl.message;

import java.io.Serializable;

import org.cyk.system.root.business.api.message.SmtpPropertiesBusiness;
import org.cyk.utility.common.message.SendMail;

public class MailSender extends SendMail.Adapter.Default implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected Void __execute__() {
		if(getProperties()==null || getProperties().isEmpty())
			setProperties(inject(SmtpPropertiesBusiness.class).findDefaultProperties());
		return super.__execute__();
	}
	
}
