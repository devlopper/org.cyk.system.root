package org.cyk.system.root.service.impl;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TemplateEngineBusiness;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.impl.FreeMarkerTemplateEngineImpl;
import org.cyk.system.root.business.impl.event.NotificationBusinessImpl;
import org.cyk.system.root.business.impl.message.MailBusinessImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class NotificationMailIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return deployment(new Class<?>[]{File.class,TemplateEngineBusiness.class,FreeMarkerTemplateEngineImpl.class,NotificationTemplate.class,
                NotificationBusiness.class,NotificationBusinessImpl.class,MailBusiness.class,MailBusinessImpl.class}).getArchive();
    }
    
    @Inject private FileBusiness fileBusiness;
    @Inject private GenericBusiness genericBusiness;
    @Inject private NotificationBusiness notificationBusiness;
    @Inject private MailBusiness mailBusiness;
    private NotificationTemplate notificationTemplate1,notificationTemplate2;
    
    @Override
    protected void populate() {
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
            notificationTemplate2.getMessage().setUri(NotificationMailIT.class.getResource("template.html").toURI()); 
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        genericBusiness.create(notificationTemplate2);
    }
    
    @Override
    protected void _execute_() {
        super._execute_();
       
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "Drogba didier");
        Notification notification = notificationBusiness.process(notificationTemplate2, params);
        mailBusiness.send(notification, "kycdev@gmail.com");
    }

    @Override
    protected void finds() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void businesses() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void create() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void delete() {
        // TODO Auto-generated method stub
        
    }

    

    @Override
    protected void read() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }

}
