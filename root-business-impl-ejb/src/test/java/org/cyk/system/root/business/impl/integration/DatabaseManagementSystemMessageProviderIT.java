package org.cyk.system.root.business.impl.integration;

import java.util.ArrayList;
import java.util.List;

import org.cyk.system.root.business.impl.validation.DatabaseManagementSystemMessageProvider;
import org.junit.Test;

public class DatabaseManagementSystemMessageProviderIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void businesses() {}
    
    @Test
    public void duplicate(){
    	List<String> tokens = new ArrayList<>();
		DatabaseManagementSystemMessageProvider.Adapter.MySql.DUPLICATE_ENTRY_FOR_KEY.matchs("Hello Duplicate entry 'a@m.com' for key 'ADDRESS'", tokens);
		assertEquals(3, tokens.size());
		assertEquals("Adresse", tokens.get(1));
		assertEquals("a@m.com", tokens.get(2));	
    }
    
    @Test
    public void cannotDelete(){
    	List<String> tokens = new ArrayList<>();
		DatabaseManagementSystemMessageProvider.Adapter.MySql.CANNOT_DELETE_OR_UPDATE_PARENT_ROW.matchs("Cannot delete or update a parent row: a foreign key constraint "
				+ "fails (`test_guidb`.`personrelationship`, CONSTRAINT `FK_PERSONRELATIONSHIP_PERSON2_IDENTIFIER` FOREIGN KEY (`PERSON2_IDENTIFIER`) REFERENCES `party` "
				+ "(`IDENTIFIER`))", tokens);
		assertEquals(2, tokens.size());
		assertEquals("Lien entre personne", tokens.get(1));
		assertEquals("personrelationship", tokens.get(2));	
		assertEquals("FK_PERSONRELATIONSHIP_PERSON2_IDENTIFIER", tokens.get(3));	
		assertEquals("PERSON2_IDENTIFIER", tokens.get(4));	
		assertEquals("party", tokens.get(5));	
    }

}
