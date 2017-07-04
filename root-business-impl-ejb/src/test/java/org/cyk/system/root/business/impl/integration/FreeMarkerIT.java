package org.cyk.system.root.business.impl.integration;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.TemplateEngineBusiness;
import org.cyk.system.root.model.file.File;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class FreeMarkerIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Inject private FileBusiness fileBusiness;
    @Inject private TemplateEngineBusiness templateEngineBusiness;
    private File template1,template2,template3;
    
    @Override
    protected void populate() {
        template1 = fileBusiness.create(fileBusiness.process("The name is : ${name}".getBytes(), "template1.txt"));
        template2 = fileBusiness.create(fileBusiness.process("Our name should be : ${name}".getBytes(), "template2.txt"));
        template3 = new File();
        try {
            template3.setUri(FreeMarkerIT.class.getResource("template.html").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        fileBusiness.create(template3);
    }
    
    @Override
    protected void _execute_() {
        super._execute_();
       
        String name;
        Map<String, Object> params = new HashMap<String, Object>();
        
        name = "Drogba";
        params.put("name", name);
        String yourNameIs = "The name is : ";
        Assert.assertEquals(yourNameIs+name,templateEngineBusiness.process(template1, params));
        
        params = new HashMap<String, Object>();
        name = "Didier";
        params.put("name", name);
        Assert.assertEquals(yourNameIs+name,templateEngineBusiness.process(template1, params));
        
        params = new HashMap<String, Object>();
        name = "Zokou";
        params.put("name", "John");
        Assert.assertNotEquals(yourNameIs+name,templateEngineBusiness.process(template1, params));
        
        params = new HashMap<String, Object>();
        params.put("name", "Trinity");
        Assert.assertEquals("Our name should be : Trinity",templateEngineBusiness.process(template2, params));
        
        params = new HashMap<String, Object>();
        params.put("name", "Trinity");
        System.out.println(templateEngineBusiness.process(template3, params));
        //Assert.assertEquals("Our name should be : Trinity",templateEngineBusiness.process(template3, params));

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
