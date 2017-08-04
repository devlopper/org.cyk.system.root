package org.cyk.system.root.business.impl.integration;

import java.util.ArrayList;
import java.util.List;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.PhoneNumber;

public class ContactCollectionBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Override
    protected void businesses() {
    	createAndUpdateOnePhoneNumber();
    	createAndUpdateManyPhoneNumbers();
    }
    
    private void createAndUpdateOnePhoneNumber(){
    	ContactCollection collection = new ContactCollection();
    	collection.setPhoneNumbers(new ArrayList<PhoneNumber>());
    	
    	collection = create(inject(ContactCollectionBusiness.class).instanciateOne(new String[]{"123456789"}, null, null, null));
    	contains(collection, new String[]{"123456789"});
    	
    	update(collection, null,new String[]{"996633"},null);
    	contains(collection, new String[]{"996633"});
    	
    }
    
    private void createAndUpdateManyPhoneNumbers(){
    	ContactCollection collection = new ContactCollection();
    	collection.setPhoneNumbers(new ArrayList<PhoneNumber>());
    	
    	collection = create(inject(ContactCollectionBusiness.class).instanciateOne(new String[]{"111","222"},null,null,null));
    	contains(collection, new String[]{"111","222"});
    	
    	update(collection, null,new String[]{"111","444"},null);
    	contains(collection, new String[]{"111","444"});
    	
    	update(collection,new String[]{"111"}, null,null);
    	contains(collection, new String[]{"444"});
    	
    	update(collection, null,null,new String[]{"111"});
    	contains(collection, new String[]{"444","111"});
    }
    
    
    
    private void removePhoneNumberInstance(ContactCollection collection,String number){
    	for(int i=0;i<collection.getPhoneNumbers().size();){
    		if( ((List<PhoneNumber>)collection.getPhoneNumbers()).get(i).getNumber().equals(number) )
    			((List<PhoneNumber>)collection.getPhoneNumbers()).remove(i);
    		else
    			i++;
    	}
    	
    	for(int i=0;i<collection.getItems().getCollection().size();){
    		if( ((List<Contact>)collection.getItems().getCollection()).get(i) instanceof PhoneNumber )
	    		if( ((PhoneNumber)((List<Contact>)collection.getItems().getCollection()).get(i)).getNumber().equals(number) )
	    			((List<Contact>)collection.getItems().getCollection()).remove(i);
	    		else
	    			i++;
    		else
    			i++;
    	}
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
    	
    	inject(PhoneNumberBusiness.class).instanciateMany(collection, numberToAdd);
	    	
    	update(collection);
    }
    
    private void contains(ContactCollection collection,String[] numbers){
    	collection = inject(ContactCollectionBusiness.class).load(collection.getIdentifier());
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
        
}
