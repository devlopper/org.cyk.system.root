package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.system.root.model.language.programming.ScriptVariable;
import org.cyk.system.root.model.language.programming.ScriptVariableCollection;
import org.cyk.system.root.persistence.api.language.programming.ScriptDao;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.junit.Test;

public class ProgrammingIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test
    public void crudScript() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("my script"));
    	testCase.clean();
    }
    
    @Test
    public void crudScriptWithVariables() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("my script").addVariableCollectionItemsByName("a","r2"));
    	testCase.assertNotNull(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Script.class, scriptCode).getVariableCollection());
    	testCase.assertCountAll(ScriptVariableCollection.class, 1);
    	testCase.assertCountAll(ScriptVariable.class, 2);
    	testCase.clean();
    }
    
    @Test
    public void assertScriptTextHasBeenSetFromBytes() {
    	TestCase testCase = instanciateTestCase();
    	testCase.assertTrue(StringHelper.getInstance().isNotBlank(inject(ScriptDao.class).readOneRandomly().getText()));
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptPrintln() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("println('THIS IS IT')"));
    	testCase.assertScriptEvaluate(scriptCode);
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptSum() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("a = 1 + 2; r2 = 10+6").addVariableCollectionItemsByName("a","r2"));
    	testCase.assertScriptEvaluate(scriptCode,"a",3,"r2",16);
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptFindScriptEvaluationEngineByCode() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText(
    			"code = genericBusiness.find('ScriptEvaluationEngine','javascript').getCode()+'';"
    			+ "name = genericBusiness.find('ScriptEvaluationEngine','javascript').getName()+'';").addVariableCollectionItemsByName("code","name"));
    	testCase.assertScriptEvaluate(scriptCode,"code","javascript","name","javascript");
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptFindScriptEvaluationEngineByCodeUsingReturnValue() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("code = genericBusiness.find('ScriptEvaluationEngine','javascript').getCode()+'';"));
    	testCase.assertScriptEvaluate(scriptCode,RootConstant.Configuration.ScriptVariable.RETURNED,"javascript");
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptIfElse() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("mystring = 'hello'; a = mystring.charAt(0)==='1' ? 'Match' : 'No matching';").addVariableCollectionItemsByName("a"));
    	testCase.assertScriptEvaluate(scriptCode,"a","No matching");
    	testCase.clean();
    }
   
	/**/
	
	@SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Script.class);
		}
		
    }
    
}
