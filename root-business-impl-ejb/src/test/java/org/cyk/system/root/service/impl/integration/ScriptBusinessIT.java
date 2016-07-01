package org.cyk.system.root.service.impl.integration;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.junit.Assert;

public class ScriptBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
     
    @Inject private ScriptBusiness scriptBusiness;
    private Script script1,script2; 
    
    @Override
    protected void populate() {
    	create(script1 = script("print('THIS IS A IT')"));
    	create(script2 = script("a = 1 + 2; r2 = 10+6","a","r2"));
    }
    
    private Script script(String text,String...variables){
        Script script = new Script();
        script.setFile(new File());
        script.getFile().setBytes(text.getBytes());
        if(variables!=null)
        	for(String variable : variables)
        		script.getVariables().add(variable);
        
        return script;
    }
    
    @Override
    protected void businesses() {
    	Map<String, Object> inputs = new HashMap<String, Object>();
        
        assertValues(script1, inputs);
        assertValues(script2, inputs,3.0,16.0);
    }
    
    /**/
    
    private void assertValues(Script script,Map<String, Object> inputs,Object...expectedValues){
    	Map<String, Object> actualValues = scriptBusiness.evaluate(script, inputs);
    	if(expectedValues!=null){
	    	int i = 0;
	    	for(String variable : script.getVariables()){
	    		Assert.assertEquals(expectedValues[i++], actualValues.get(variable));
	    	}
    	}
    }

}
