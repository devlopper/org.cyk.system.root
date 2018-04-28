package org.cyk.system.root.business.impl.integration;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.model.file.ScriptVariableCollection;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.party.person.SexDao;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class FileIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    /* File */
    
    @Test
    public void crudFileUsingUniformResourceIdentifier() {
    	TestCase testCase = instanciateTestCase();
    	String fileCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(File.class,fileCode).setUniformResourceIdentifier("http://myresource"));
    	testCase.clean();
    }
    
    @Test
    public void crudFileUsingBytes() throws IOException {
    	TestCase testCase = instanciateTestCase();
    	String fileCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(File.class,fileCode).setBytesFromInputStream(getClass().getResourceAsStream("file/pointofsale.pdf")));
    	testCase.clean();
    }
    
    @Test
    public void crudFileUsingString() throws IOException {
    	TestCase testCase = instanciateTestCase();
    	String fileCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(File.class,fileCode).setBytesFromString("This is a text."));
    	testCase.clean();
    }
    
    @Test
    public void throwFileBytesOrUniformResourceIdentifierIsNull() {
    	TestCase testCase = instanciateTestCase();
    	String fileCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(File.class,fileCode),null
    			,"La valeur de l'attribut <<octets ou identifiant uniforme de resource>> de l'entité <<fichier>> doit être non nulle.");
    	testCase.clean();
    }
    
    /* Script */
    
    @Test
    public void crudScript() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setFileBytesFromString("my script"));
    	testCase.clean();
    }
    
    @Test
    public void crudScriptWithVariables() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setFileBytesFromString("my script").addVariableCollectionItemsByName("a","r2"));
    	testCase.assertNotNull(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Script.class, scriptCode).getVariableCollection());
    	testCase.assertCountAll(ScriptVariableCollection.class, 1);
    	testCase.assertCountAll(ScriptVariable.class, 2);
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptPrintln() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setFileBytesFromString("println('THIS IS IT')"));
    	testCase.assertScriptEvaluate(scriptCode);
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptSum() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setFileBytesFromString("a = 1 + 2; r2 = 10+6").addVariableCollectionItemsByName("a","r2"));
    	testCase.assertScriptEvaluate(scriptCode,"a",3,"r2",16);
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptFindScriptEvaluationEngineByCode() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setFileBytesFromString(
    			"code = genericBusiness.find('ScriptEvaluationEngine','javascript').getCode()+'';"
    			+ "name = genericBusiness.find('ScriptEvaluationEngine','javascript').getName()+'';").addVariableCollectionItemsByName("code","name"));
    	testCase.assertScriptEvaluate(scriptCode,"code","javascript","name","javascript");
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptFindScriptEvaluationEngineByCodeUsingReturnValue() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setFileBytesFromString(
    			"code = genericBusiness.find('ScriptEvaluationEngine','javascript').getCode()+'';"));
    	testCase.assertScriptEvaluate(scriptCode,RootConstant.Configuration.ScriptVariable.RETURNED,"javascript");
    	testCase.clean();
    }
    
    @Test
    public void evaluateScriptIfElse() {
    	TestCase testCase = instanciateTestCase();
    	String scriptCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Script.class,scriptCode).setFileBytesFromString("mystring = 'hello'; a = mystring.charAt(0)==='1' ? 'Match' : 'No matching';").addVariableCollectionItemsByName("a"));
    	testCase.assertScriptEvaluate(scriptCode,"a","No matching");
    	testCase.clean();
    }
    
	@Override
	protected void businesses() {
		FileBusiness fileBusiness = inject(FileBusiness.class);
		
		assertEquals("image/jpeg", fileBusiness.findMime("jpg"));
		
		try {
			File file1 = fileBusiness.process(IOUtils.toByteArray(getClass().getResourceAsStream("file/pointofsale.pdf")), "pointofsale.pdf");
			file1.setRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_IMAGE));
			create(file1);
			file1 = fileBusiness.find(file1.getIdentifier());
			
			File file2 = fileBusiness.process(IOUtils.toByteArray(getClass().getResourceAsStream("file/sequencediagram.pdf")), "sequencediagram.pdf");
			file2.setRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_DOCUMENT));
			create(file2);
			file2 = fileBusiness.find(file2.getIdentifier());
			
			Sex sexMale = inject(SexDao.class).read(RootConstant.Code.Sex.MALE);
			Sex sexFemale = inject(SexDao.class).read(RootConstant.Code.Sex.FEMALE);
			
			create(new FileIdentifiableGlobalIdentifier(file1,sexMale));
			create(new FileIdentifiableGlobalIdentifier(file2,sexMale));
			create(new FileIdentifiableGlobalIdentifier(file1,sexFemale));
			create(new FileIdentifiableGlobalIdentifier(file2,sexFemale));
			
			FileIdentifiableGlobalIdentifierBusiness fileIdentifiableGlobalIdentifierBusiness = inject(FileIdentifiableGlobalIdentifierBusiness.class);
			
			assertEquals(4l, fileIdentifiableGlobalIdentifierBusiness.countAll());
			
			FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria;
			searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
			searchCriteria.addGlobalIdentifier(sexMale.getGlobalIdentifier());
			searchCriteria.addGlobalIdentifier(sexFemale.getGlobalIdentifier());
			Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = fileIdentifiableGlobalIdentifierBusiness.findByCriteria(searchCriteria);
			assertEquals(4, fileIdentifiableGlobalIdentifiers.size());
			
			searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
			searchCriteria.addRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_DOCUMENT));
			searchCriteria.addGlobalIdentifier(sexMale.getGlobalIdentifier());
			searchCriteria.addGlobalIdentifier(sexFemale.getGlobalIdentifier());
			fileIdentifiableGlobalIdentifiers = fileIdentifiableGlobalIdentifierBusiness.findByCriteria(searchCriteria);
			assertEquals(2, fileIdentifiableGlobalIdentifiers.size());
			
			searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
			searchCriteria.addGlobalIdentifier(sexMale.getGlobalIdentifier());
			searchCriteria.addRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_DOCUMENT));
			fileIdentifiableGlobalIdentifiers = fileIdentifiableGlobalIdentifierBusiness.findByCriteria(searchCriteria);
			assertEquals(1, fileIdentifiableGlobalIdentifiers.size());
			
			fileBusiness.writeTo(fileIdentifiableGlobalIdentifiers.iterator().next().getFile(), new java.io.File(System.getProperty("user.dir")+"/target"), "mypointofsale");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
   
	/**/
	
	@SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(File.class);
		}
		
    }
    
}
