package org.cyk.system.root.business.impl.integration;

import java.net.URISyntaxException;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.impl.data.Data;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class NotificationBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Inject private FileBusiness fileBusiness;
    @Inject private NotificationBusiness notificationBusiness;
    @Inject private NotificationTemplateDao notificationTemplateDao;
    @Inject private MailBusiness mailBusiness;
    
    private NotificationTemplate notificationTemplate1,notificationTemplate2;
        
    @Override
    protected void _execute_() {
        super._execute_();
        installApplication();
        notificationTemplate1 = new NotificationTemplate();
        notificationTemplate1.setCode("NTC1");
        notificationTemplate1.setName("NTN1");
        notificationTemplate1.setTitle(fileBusiness.process("Hello ${name}".getBytes(), "template1.txt"));
        notificationTemplate1.setMessage(fileBusiness.process("The name is : ${name}".getBytes(), "template1.txt"));
        genericBusiness.create(notificationTemplate1);
        
        notificationTemplate2 = new NotificationTemplate();
        notificationTemplate2.setCode("NTC2");
        notificationTemplate2.setName("NTN2");
        notificationTemplate2.setTitle(fileBusiness.process("Second Temp , Hello ${name}".getBytes(), "template1.txt"));
        notificationTemplate2.setMessage(new File());
        try {
            notificationTemplate2.getMessage().setUri(Data.class.getResource("template.html").toURI()); 
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        genericBusiness.create(notificationTemplate2);
        
        NotificationTemplate nt = notificationTemplateDao.readByGlobalIdentifierCode(RootConstant.Code.NotificationTemplate.ALARM_USER_INTERFACE);
        nt.getTitleParametersMap().put("title", "Drogba didier");
        nt.getMessageParametersMap().put("body", "The Big Manager");
        
        Notification notification = new Notification();
        notificationBusiness.fill(notification,nt);
        
        //System.setProperty("socksProxyHost", "10.100.100.151");
        //System.setProperty("socksProxyPort", "3128");
        //System.out.println(System.getProperty("http.proxyHost"));
        //System.out.println(System.getProperty("http.proxyPort"));
        MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
        mailBusiness.send(notification, "kycdev@gmail.com");
    }

	@Override
	protected void businesses() {
		// TODO Auto-generated method stub
		
	}

}
