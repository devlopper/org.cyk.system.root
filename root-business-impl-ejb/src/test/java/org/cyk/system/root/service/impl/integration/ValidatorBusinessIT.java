package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.Person;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class ValidatorBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment(); 
    } 
    
    @Inject private PersonBusiness personBusiness;
    @Inject private FileBusiness fileBusiness;
    @Inject private PersonValidator personValidator;
    @Inject private FileValidator fileValidator;
    
    @Override
    protected void populate() {
        validatorMap.registerValidator(Person.class, personValidator);
        validatorMap.registerValidator(File.class, fileValidator);
        personValidator.getFileValidator().getExtensions().add("jpeg");
    	
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
    
    /**/
    
    private Person person(){
    	Person person = new Person();
        person.setName("0paul");
        File file = new File();
        file.setBytes(new byte[1024 * 1024 * 1]);
        file.setExtension("png");
        person.setImage(file);
        return person;
    }
    
    private File file(){
    	File file = new File();
        file.setBytes(new byte[1024 * 1024 * 1]);
        file.setExtension("txt");
        return file;
    }

}
