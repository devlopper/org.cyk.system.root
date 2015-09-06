package org.cyk.system.root.service.impl.integration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.impl.RootRandomDataProvider;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class ContactCollectionBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Inject private ContactCollectionBusiness contactCollectionBusiness;
    
    @Override
    protected void businesses() {
    	installApplication();
    	
    	createAndUpdateOnePhoneNumber();
    	createAndUpdateManyPhoneNumbers();
    }
    
    private void createAndUpdateOnePhoneNumber(){
    	ContactCollection collection = new ContactCollection();
    	collection.setPhoneNumbers(new ArrayList<PhoneNumber>());
    	
    	collection = create(collection,new String[]{"123456789"});
    	contains(collection, new String[]{"123456789"});
    	
    	update(collection, null,new String[]{"996633"},null);
    	contains(collection, new String[]{"996633"});
    	
    }
    
    private void createAndUpdateManyPhoneNumbers(){
    	ContactCollection collection = new ContactCollection();
    	collection.setPhoneNumbers(new ArrayList<PhoneNumber>());
    	
    	collection = create(collection,new String[]{"111","222"});
    	contains(collection, new String[]{"111","222"});
    	
    	update(collection, null,new String[]{"111","444"},null);
    	contains(collection, new String[]{"111","444"});
    	
    	update(collection,new String[]{"111"}, null,null);
    	contains(collection, new String[]{"444"});
    	
    	update(collection, null,null,new String[]{"111"});
    	contains(collection, new String[]{"444","111"});
    }
    
    private PhoneNumber instancePhoneNumber(ContactCollection collection,String number){
    	PhoneNumber phoneNumber = RootRandomDataProvider.getInstance().phoneNumber(collection);
    	phoneNumber.setNumber(number);
    	return phoneNumber;
    }
    
    private void removePhoneNumberInstance(ContactCollection collection,String number){
    	for(int i=0;i<collection.getPhoneNumbers().size();){
    		if( ((List<PhoneNumber>)collection.getPhoneNumbers()).get(i).getNumber().equals(number) )
    			((List<PhoneNumber>)collection.getPhoneNumbers()).remove(i);
    		else
    			i++;
    	}
    }
    
    private ContactCollection create(ContactCollection collection,String[] phoneNumbers){
    	for(String number : phoneNumbers){
    		instancePhoneNumber(collection, number);
    	}
    	contactCollectionBusiness.create(collection);
    	return contactCollectionBusiness.load(collection.getIdentifier());
    }
    
    private void update(ContactCollection collection,String[] numberToRemove,String[] numberToEdit,String[] numberToAdd){
    	
    	if(numberToRemove!=null)
	    	for(int i=0;i<numberToRemove.length;i++){
	    		removePhoneNumberInstance(collection, numberToRemove[i]);
	    	}
    	
    	if(numberToEdit!=null)
	    	for(int i=0;i<numberToEdit.length;i++){
	    		PhoneNumber phoneNumber = ((List<PhoneNumber>)collection.getPhoneNumbers()).get(i);
	    		phoneNumber.setNumber(numberToEdit[i]);
	    	}
    	
    	if(numberToAdd!=null)
	    	for(int i=0;i<numberToAdd.length;i++){
	    		PhoneNumber phoneNumber = RootRandomDataProvider.getInstance().phoneNumber(collection);
	    		phoneNumber.setNumber(numberToAdd[i]);
	    	}
    	
    	contactCollectionBusiness.update(collection);
    }
    
    private void contains(ContactCollection collection,String[] numbers){
    	collection = contactCollectionBusiness.load(collection.getIdentifier());
    	Object[][] values = new Object[numbers.length][1];
    	for(int i = 0;i<numbers.length;i++){
    		values[i][0] = numbers[i];
    		//values[i][1] = collection;
    	}
    	
    	assertThat("Number of phone numbers", collection.getPhoneNumbers().size()==numbers.length);
    	contains(PhoneNumber.class, collection.getPhoneNumbers(), new Object[]{"number"}, values);
    	
    	for(PhoneNumber phoneNumber : collection.getPhoneNumbers())
    		assertThat("Belongs to collection", phoneNumber.getCollection().equals(collection));
    }
    
    @Override protected void finds() {}
    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}
    
    
}
