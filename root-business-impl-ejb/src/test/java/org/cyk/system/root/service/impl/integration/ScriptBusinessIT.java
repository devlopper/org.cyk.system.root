package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class ScriptBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }  
     
    @Inject private ScriptBusiness scriptBusiness;
    private Script script1,script2; 
    
    @Override
    protected void populate() {
    	create(script1 = script("print('THIS IS A IT')"));
    }
    
    private Script script(String text){
        Script script = new Script();
        script.setFile(new File());
        script.getFile().setBytes(text.getBytes());
        return script;
    }

    
    @Override
    protected void _execute_() {
        super._execute_();
        scriptBusiness.evaluate(script1, null);
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
