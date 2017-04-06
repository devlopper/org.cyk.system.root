package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.persistence.api.file.ScriptEvaluationEngineDao;

public class ScriptBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
     
    private Script script1,script2,script3,script4,script5; 
    
    @Override
    protected void populate() {
    	super.populate();
    	create(script1 = script("print('THIS IS A IT')"));
    	create(script2 = script("a = 1 + 2; r2 = 10+6","a","r2"));
    	create(script3 = script("business.find('Country','FJ').getName();"));
    	create(script4 = script("business.find('Locality','FJ');"));
    	create(script5 = script("mystring = 'hello'; mystring.charAt(0)='h'"));
    }
    
    private Script script(String text,String...variables){
        Script script = new Script();
        script.setFile(new File());
        script.getFile().setBytes(text.getBytes());
        script.setEvaluationEngine(inject(ScriptEvaluationEngineDao.class).read(RootConstant.Code.ScriptEvaluationEngine.JAVASCRIPT));
        script.getVariables().setSynchonizationEnabled(Boolean.TRUE);
        for(String v : variables){
        	ScriptVariable scriptVariable = new ScriptVariable();
        	scriptVariable.setCode(v);
        	scriptVariable.setScript(script);
        	script.getVariables().getCollection().add(scriptVariable);
        }
        return script;
    }
    
    @Override
    protected void businesses() {
    	assertValues(script1);
        assertValues(script2,"a",3.0,"r2",16.0);
        //assertValues(script3);
        //assertValues(script4);
        assertValues(script5,"false");
    }
    
    /**/
    
    private void assertValues(Script script,Object...expectedValues){
    	inject(ScriptBusiness.class).evaluate(script);
    	if(expectedValues!=null){
	    	for(int i = 0;i<expectedValues.length;i=i+2)
	    	 	assertEquals(expectedValues[i].toString(),expectedValues[i+1], script.getOutputs().get(expectedValues[i]));
    	}
    }

}
