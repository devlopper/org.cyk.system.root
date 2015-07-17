package org.cyk.system.root.service.impl.integration;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class LanguageBusinessIT extends AbstractBusinessIT {
	
	private static final long serialVersionUID = -348335622740544991L;
	@Inject private LanguageBusiness languageBusiness;
 
	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	}
			
    @Override
    protected void create() {
        /*
    	Language l = new Language("NN", "NEANT");
        languageBusiness.create(l);
        System.out.println(ToStringBuilder.reflectionToString(l));
        assertNotNull(languageBusiness.find(l.getIdentifier()));
        */
    }

    @Override
    protected void read() {
      // assertEquals(1, languageBusiness.find(Function.COUNT).where("code", "NN").all().size());
    }
    
    @Override
    protected void update() {
        /*
    	Language l = languageBusiness.find().where("code", "NN").one();
        l.setName("MyLanguage"); 
        languageBusiness.update(l);
        assertNotNull(languageBusiness.find().where("label", "MyLanguage") );
        */
    }
    
    @Override
    protected void delete() {
    	/*
        Language l = languageBusiness.find().where("code", "NN").one();
        languageBusiness.delete(l);
        assertNull(languageBusiness.find(l.getIdentifier()));
        */
    }
	
	@Test
	public void noFetch() {
	    assertTrue("bonjour".equals(languageBusiness.findText( Locale.FRENCH,"good.morning")));
        assertTrue("good morning".equals(languageBusiness.findText(Locale.ENGLISH,"good.morning")));
	}
	
	@Test
    public void fetchFromPropertiesFile() {
	    assertTrue("##hello##".equals(languageBusiness.findText( Locale.FRENCH,"hello")));
        assertTrue("##hello##".equals(languageBusiness.findText(Locale.ENGLISH,"hello")));
    }
	
	@Test
    public void fieldText() {
	    assertEquals("Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyUIClass.class, "user", Boolean.TRUE))); 
	    assertEquals("Quantite de Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyUIClass.class, "userQuantity", Boolean.TRUE))); 
	    assertEquals("Prix de Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyUIClass.class, "userPrice", Boolean.TRUE))); 
	    assertEquals("Prix unitaire de Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyUIClass.class, "userUnitPrice", Boolean.TRUE))); 
	    assertEquals("Nombre de Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyUIClass.class, "userCount", Boolean.TRUE))); 
    }
	
	/*
	@Test
    public void fetchFromDatabase() {
	    
	}
	
	@Test
    public void fetchFromGoogle() {
	    
	}*/
	
    @Override
    protected void finds() {
        
        
    }

    @Override
    protected void businesses() {
    	assertEquals("Le", languageBusiness.findDeterminantText(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE));
    	assertEquals("Un", languageBusiness.findDeterminantText(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));
    	assertEquals("Les", languageBusiness.findDeterminantText(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE));
    	assertEquals("Des", languageBusiness.findDeterminantText(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));
    	
    	assertEquals("La", languageBusiness.findDeterminantText(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE));
    	assertEquals("Une", languageBusiness.findDeterminantText(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
    	assertEquals("Les", languageBusiness.findDeterminantText(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE));
    	assertEquals("Des", languageBusiness.findDeterminantText(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
    }

    /**/
    
    public static class MyUIClass{
    	
    	@Input private String user;
    	@Input private String userQuantity;
    	@Input private String userPrice;
    	@Input private String userUnitPrice;
    	@Input private String userCount;
    	
    }


}
