package org.cyk.system.root.service.impl.integration;

import java.net.URISyntaxException;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.cyk.system.root.service.impl.data.Data;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class NotificationMailIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Inject private FileBusiness fileBusiness;
    @Inject private NotificationBusiness notificationBusiness;
    @Inject private NotificationTemplateDao notificationTemplateDao;
    @Inject private MailBusiness mailBusiness;
    @Inject private ApplicationBusiness applicationBusiness;
    
    private Installation installation;
    private NotificationTemplate notificationTemplate1,notificationTemplate2;
    
    @Override
    protected void populate() {
    	installation = RootBusinessLayer.fakeInstallation();
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
    }
    
    @Override
    protected void _execute_() {
        super._execute_();
        applicationBusiness.install(installation);
        
        NotificationTemplate nt = notificationTemplateDao.read(NotificationTemplate.ALARM_USER_INTERFACE);
        nt.getTitleParametersMap().put("title", "Drogba didier");
        nt.getMessageParametersMap().put("body", "The Big Manager");
        
        Notification notification = new Notification();
        notificationBusiness.fill(notification,nt);
        mailBusiness.send(notification, "kycdev@gmail.com");
    }

    @Override
    protected void finds() {
        
        
    }

    @Override
    protected void businesses() {
        
    }

    @Override
    protected void create() {
        
    }

    @Override
    protected void delete() {
        
    }

    

    @Override
    protected void read() {
        
    }

    @Override
    protected void update() {
        
    }

}
