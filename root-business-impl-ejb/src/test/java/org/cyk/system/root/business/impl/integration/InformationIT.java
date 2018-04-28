package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.information.EntityPropertyBusiness;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.information.Entity;
import org.cyk.system.root.model.information.EntityProperty;
import org.cyk.system.root.model.information.Property;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class InformationIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    /* File */
    
    @Test
    public void crudEntity() {
    	TestCase testCase = instanciateTestCase();
    	String entityCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Entity.class,entityCode));
    	testCase.clean();
    }
    
    @Test
    public void crudProperty() {
    	TestCase testCase = instanciateTestCase();
    	String propertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Property.class,propertyCode));
    	testCase.clean();
    }
    
    @Test
    public void crudEntityProperty() {
    	TestCase testCase = instanciateTestCase();
    	
    	String entityCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Entity.class,entityCode));
    	
    	String propertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Property.class,propertyCode));
    	
    	String entityPropertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(EntityProperty.class,entityPropertyCode).setEntityFromCode(entityCode).setPropertyFromCode(propertyCode));
    	testCase.clean();
    }
    
    @Test
    public void evaluateEntityPropertyValueGeneratorScript() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setFileBytesFromString("'thecode'"));
    	
    	String entityCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Entity.class,entityCode));
    	
    	String propertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Property.class,propertyCode));
    	
    	String entityPropertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(EntityProperty.class,entityPropertyCode).setEntityFromCode(entityCode).setPropertyFromCode(propertyCode)
    			.setValueGeneratorScriptFromCode(scriptCode));
    	
    	testCase.assertEquals("thecode", inject(EntityPropertyBusiness.class).evaluate(entityCode, propertyCode, null));
    	testCase.clean();
    }
    
    @Test
    public void evaluatePersonCode_initials_01() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setFileBytesFromString("stringHelper.concatenateFirstLetterOfEach(instance.name+' '+instance.lastnames)"));
    	
    	Person person = testCase.instanciateOne(Person.class).setName("Komenan").setLastnames("Yao Christian");
    	
    	testCase.assertEquals(null, inject(EntityPropertyBusiness.class).evaluate(RootConstant.Code.Entity.PERSON, RootConstant.Code.Property.CODE, person));
    	
    	String entityPropertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(EntityProperty.class,entityPropertyCode).setEntityFromCode(RootConstant.Code.Entity.PERSON)
    			.setPropertyFromCode(RootConstant.Code.Property.CODE).setValueGeneratorScriptFromCode(scriptCode));
    	
    	testCase.update(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(EntityProperty.class,entityPropertyCode).setValueGeneratorScriptFromCode(scriptCode));
    	
    	testCase.assertEquals("KYC", inject(EntityPropertyBusiness.class).evaluate(RootConstant.Code.Entity.PERSON, RootConstant.Code.Property.CODE, person));
    	
    	testCase.clean();
    }
       
	/**/
	
	@SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Entity.class,File.class,Party.class);
		}
		
    }
    
}
