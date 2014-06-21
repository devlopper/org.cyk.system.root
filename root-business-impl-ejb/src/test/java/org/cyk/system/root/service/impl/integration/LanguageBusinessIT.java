package org.cyk.system.root.service.impl.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.utility.common.computation.Function;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class LanguageBusinessIT extends AbstractBusinessIT {
	
	@Inject private LanguageBusiness languageBusiness;
 
	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{Language.class,LanguageEntry.class}).getArchive();
	}
		
	@Override
    protected void populate() {
	    try {
            languageBusiness.create(new Language("FR", "Francais"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        languageBusiness.create(new Language("EN", "Anglais"));
        languageBusiness.create(new Language("AL", "Allemand"));
        languageBusiness.create(new Language("ES", "Espagnol"));
    }
	
    @Override
    protected void create() {
        Language l = new Language("NN", "NEANT");
        languageBusiness.create(l);
        System.out.println(ToStringBuilder.reflectionToString(l));
        assertNotNull(languageBusiness.find(l.getIdentifier()));
    }

    @Override
    protected void read() {
       assertEquals(1, languageBusiness.find(Function.COUNT).where("code", "NN").all().size());
    }
    
    @Override
    protected void update() {
        Language l = languageBusiness.find().where("code", "NN").one();
        l.setName("MyLanguage"); 
        languageBusiness.update(l);
        assertNotNull(languageBusiness.find().where("label", "MyLanguage") );
    }
    
    @Override
    protected void delete() {
        Language l = languageBusiness.find().where("code", "NN").one();
        languageBusiness.delete(l);
        assertNull(languageBusiness.find(l.getIdentifier()));
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
        // TODO Auto-generated method stub
        
    }

    /**/
    
    


}
