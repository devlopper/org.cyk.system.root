package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.RootValueValidator;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.Person;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment(); 
    } 
    
    @Inject private PersonBusiness personBusiness;
    @Inject private FileBusiness fileBusiness;
    
    @Inject private RootBusinessLayer rootBusinessLayer;
    @Inject private RootValueValidator rootValueValidator;
    
    @Override
    protected void populate() {
        System.out.println(rootBusinessLayer);
    }
    
    @Override
    protected void _execute_() {
    	super._execute_();
    	Assert.assertTrue(rootValueValidator.isValidPersonName("paul"));
    	Assert.assertFalse(rootValueValidator.isValidPersonName("0paul"));
    	Assert.assertFalse(rootValueValidator.isValidPersonName("p0aul"));
    	Assert.assertFalse(rootValueValidator.isValidPersonName("pa0ul"));
    	Assert.assertFalse(rootValueValidator.isValidPersonName("pau0l"));
    	Assert.assertFalse(rootValueValidator.isValidPersonName("paul0"));
    	
    	Assert.assertTrue(rootValueValidator.isValidFileExtension("txt"));
    	Assert.assertFalse(rootValueValidator.isValidFileExtension("file"));
    	
    	Assert.assertTrue(rootValueValidator.isValidFileSize(1l * 1024 * 1024 * 1));
    	Assert.assertFalse(rootValueValidator.isValidFileSize(1l * 1024 * 1024 * 1024*1024*1024));
    }
    
    
    @Test(expected=Exception.class)
    public void validatePersonName(){
    	Person person = person();
        person.setName("0paul");
    	personBusiness.create(person);
    }
    
    @Test(expected=Exception.class)
    public void validatePersonImageExtension(){
    	Person person = person();
        person.getImage().setExtension("123");
    	personBusiness.create(person);
    }
    
    @Test(expected=Exception.class)
    public void validatePersonImageSize(){
    	Person person = person();
        person.getImage().setBytes(new byte[1024 * 1024 * 10]);
    	personBusiness.create(person);
    }
    
    @Test(expected=Exception.class)
    public void validateFileExtension(){
    	File file = file();
        file.setExtension("123");
        fileBusiness.create(file);
    }
    
    @Test(expected=Exception.class)
    public void validateFileSize(){
    	File file = file();
        file.setBytes(new byte[1024 * 1024 * 100]);
        fileBusiness.create(file);
    }
    

    @Override
    protected void finds() {}
    @Override
    protected void businesses() {}
    @Override
    protected void create() {}
    @Override
    protected void delete() {}
    @Override
    protected void read() {}
    @Override
    protected void update() {}
    
    /**/
    
    protected void validate(Runnable runnable){
    	try{
    		runnable.run();
    	}catch(Exception exception){
    		System.out.println(exception);
    	}
    }
    
    private Person person(){
    	Person person = new Person();
        person.setName("paul");
        person.setImage(file());
        person.getImage().setBytes(new byte[1024 * 1024 * 1]);
        person.getImage().setExtension("png");
        return person;
    }
    
    private File file(){
    	File file = new File();
        file.setBytes(new byte[1024 * 1024 * 1]);
        file.setExtension("txt");
        return file;
    }

}
