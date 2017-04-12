package org.cyk.system.root.business.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendArguments;
import org.cyk.system.root.business.impl.file.FileBusinessImpl;
import org.cyk.system.root.business.impl.message.MailBusinessImpl;
import org.cyk.system.root.business.impl.message.SmtpPropertiesBusinessImpl;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MailBusinessPingUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private MailBusinessImpl mailBusiness;
	@InjectMocks private FileBusinessImpl fileBusiness;
	@InjectMocks private SmtpPropertiesBusinessImpl smtpPropertiesBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(mailBusiness);
		collection.add(fileBusiness);
		collection.add(smtpPropertiesBusiness);
		//MailBusinessImpl.SMTP_PROPERTIES = new SmtpProperties();
		//MailBusinessImpl.SMTP_PROPERTIES.setCredentials(new Credentials());
		//mailBusiness.setProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*");
		//mailBusiness.setProperties("smtp.iesaci.com", 25, "results@iesaci.com", "school2009");
		//mailBusiness.setProperties("smtp.gmail.com", 465, "soldesigdcp@gmail.com", "sigdcp1234");
		//mailBusiness.setProperties("smtpauth.myorangeoffice.com", 465, "results@iesa-ci.com", "17abIESAresults");
		//mailBusiness.setProperties("smtp.gmail.com", 465, "iesaciresults@gmail.com", "17abIESAresults");
	}
	
	private void ping(String host,Integer port,String username,String password) {
		SendArguments sendArguments = new SendArguments();
		sendArguments.setProperties(smtpPropertiesBusiness.convertToProperties(host, port, username, password));
		mailBusiness.ping(new String[]{username},sendArguments);
	}
	
	@Test
	public void pingGmailKycdev() {
		ping("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*");
	}
	
	//@Test
	public void pingOrangeIesaResults() {
		ping("smtpauth.myorangeoffice.com", 465, "results@iesa-ci.com", "17abIESAresults");
	}
	
	@Test
	public void pingGmailIesaResults() {
		ping("smtp.gmail.com", 465, "iesaciresults@gmail.com", "17abIESAresults");
	}
}
