package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.metadata.EntityPropertyBusiness;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.system.root.model.metadata.Entity;
import org.cyk.system.root.model.metadata.EntityProperty;
import org.cyk.system.root.model.metadata.Property;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.junit.Test;

public class MetadataIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
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
    	testCase.create(testCase.instanciateOne(Property.class,propertyCode).setPath("mypath"));
    	testCase.clean();
    }
    
    @Test
    public void crudEntityProperty() {
    	TestCase testCase = instanciateTestCase();
    	
    	String entityCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Entity.class,entityCode));
    	
    	String propertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Property.class,propertyCode).setPath("mypath"));
    	
    	String entityPropertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(EntityProperty.class,entityPropertyCode).setEntityFromCode(entityCode).setPropertyFromCode(propertyCode));
    	testCase.clean();
    }
    
    @Test
    public void evaluateEntityPropertyValueGeneratorScript() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("'thecode'"));
    	
    	String entityCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Entity.class,entityCode));
    	
    	String propertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Property.class,propertyCode).setPath("globalIdentifier.code"));
    	
    	String entityPropertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(EntityProperty.class,entityPropertyCode).setEntityFromCode(entityCode).setPropertyFromCode(propertyCode)
    			.setValueGeneratorScriptFromCode(scriptCode));
    	
    	Person person = new Person();
    	testCase.assertEquals("thecode", inject(EntityPropertyBusiness.class).evaluate(entityCode, propertyCode,person));
    	testCase.assertEquals("thecode", person.getCode());
    	testCase.clean();
    }
    
    @Test
    public void evaluatePersonCode_initials_01() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("stringHelper.concatenateFirstLetterOfEach(instance.name+' '+instance.lastnames)"));
    	
    	Person person = testCase.instanciateOne(Person.class).setName("Komenan").setLastnames("Yao Christian");
    	
    	testCase.assertEquals(null, inject(EntityPropertyBusiness.class).evaluate(RootConstant.Code.Property.CODE, person));
    	
    	String entityPropertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(EntityProperty.class,entityPropertyCode).setEntityFromCode(RootConstant.Code.Entity.PERSON)
    			.setPropertyFromCode(RootConstant.Code.Property.CODE));
    	
    	testCase.assertEquals(null, inject(EntityPropertyBusiness.class).evaluate(RootConstant.Code.Property.CODE, person));
    	
    	testCase.update(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(EntityProperty.class,entityPropertyCode).setValueGeneratorScriptFromCode(scriptCode));
    	
    	testCase.assertEquals("KYC", inject(EntityPropertyBusiness.class).evaluate(RootConstant.Code.Property.CODE, person));
    	testCase.assertEquals("KYC", person.getCode());
    	
    	testCase.clean();
    }
    
    @Test
    public void createPersonWithCodeScriptSet() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("stringHelper.concatenateFirstLetterOfEach(instance.name+' '+instance.lastnames)"));
    	String entityPropertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(EntityProperty.class,entityPropertyCode).setEntityFromCode(RootConstant.Code.Entity.PERSON)
    			.setPropertyFromCode(RootConstant.Code.Property.CODE).setValueGeneratorScriptFromCode(scriptCode));
    	
    	String personCode = testCase.create(testCase.instanciateOne(Person.class).setName("Komenan").setLastnames("Yao Christian")).getCode();
    	
    	testCase.assertEquals("KYC", personCode);
    	testCase.assertEquals("KYC", testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Person.class, personCode).getCode());
    	
    	testCase.clean();
    }
    
    @Test
    public void computeChangesOfPersonCodeWithScriptSet() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setText("stringHelper.concatenateFirstLetterOfEach(instance.name+' '+instance.lastnames)"));
    	String entityPropertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(EntityProperty.class,entityPropertyCode).setEntityFromCode(RootConstant.Code.Entity.PERSON)
    			.setPropertyFromCode(RootConstant.Code.Property.CODE).setValueGeneratorScriptFromCode(scriptCode));
    	
    	Person person = testCase.instanciateOne(Person.class).setName("Komenan").setLastnames("Yao Christian");
    	
    	testCase.assertEquals("KYC", InstanceHelper.getInstance().computeChanges(person).getCode());
    	testCase.assertEquals("KYBE", InstanceHelper.getInstance().computeChanges(person.setLastnames("Yao Bryan Emmanuel")).getCode());
    	testCase.assertEquals("Zby", InstanceHelper.getInstance().computeChanges(person.setName("Zadi").setLastnames("bléou yves")).getCode());
    	
    	testCase.clean();
    }
    
    @Test
    public void evaluatePersonCodeNoSrciptSet() {
    	TestCase testCase = instanciateTestCase();
    	String entityPropertyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(EntityProperty.class,entityPropertyCode).setEntityFromCode(RootConstant.Code.Entity.PERSON)
    			.setPropertyFromCode(RootConstant.Code.Property.CODE));
    	
    	Person person = testCase.instanciateOne(Person.class).setCode("001").setName("Komenan").setLastnames("Yao Christian");
    	testCase.assertEquals(null, inject(EntityPropertyBusiness.class).evaluate(RootConstant.Code.Property.CODE, person));
    	testCase.assertEquals("001", person.getCode());
    	
    	person.setCode(null);
    	testCase.assertEquals(null, inject(EntityPropertyBusiness.class).evaluate(RootConstant.Code.Property.CODE, person));
    	testCase.assertEquals(null, person.getCode());
    	
    	testCase.clean();
    }
       
	/**/
	
	@SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Entity.class,Script.class);
		}
		
    }
    
}
