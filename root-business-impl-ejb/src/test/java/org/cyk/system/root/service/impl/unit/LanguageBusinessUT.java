package org.cyk.system.root.service.impl.unit;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class LanguageBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private LanguageBusinessImpl languageBusiness;
	private Long performanceMaximumNumberOfCall = 100000l;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(languageBusiness);
		//languageBusiness.setCachingEnabled(Boolean.FALSE);
	}
	
	@Test
	public void findText() {
	    assertTrue("bonjour".equals(languageBusiness.findText( Locale.FRENCH,"good.morning")));
        assertTrue("good morning".equals(languageBusiness.findText(Locale.ENGLISH,"good.morning")));
        
        assertTrue("##hello##".equals(languageBusiness.findText( Locale.FRENCH,"hello")));
        assertTrue("##hello##".equals(languageBusiness.findText(Locale.ENGLISH,"hello")));
	}

	@Test
    public void findFieldLabelText() {
		assertEquals("Nombre de Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "userCount", Boolean.TRUE)));
	    assertEquals("Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "user", Boolean.TRUE))); 
	    assertEquals("Quantite de Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "userQuantity", Boolean.TRUE))); 
	    assertEquals("Prix de Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "userPrice", Boolean.TRUE))); 
	    assertEquals("Prix unitaire de Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "userUnitPrice", Boolean.TRUE))); 
	    assertEquals("Couleur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "color", Boolean.TRUE)));
	    assertEquals("Une autre couleur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "color2", Boolean.TRUE)));
    }
	
	@Test
    public void findDeterminantText() {
    	assertEquals("Le", languageBusiness.findDeterminantText(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE));
    	assertEquals("Un", languageBusiness.findDeterminantText(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));
    	assertEquals("Les", languageBusiness.findDeterminantText(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE));
    	assertEquals("Des", languageBusiness.findDeterminantText(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));
    	
    	assertEquals("La", languageBusiness.findDeterminantText(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE));
    	assertEquals("Une", languageBusiness.findDeterminantText(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
    	assertEquals("Les", languageBusiness.findDeterminantText(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE));
    	assertEquals("Des", languageBusiness.findDeterminantText(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
    }
	
	@Test
    public void findCrudText() {
		assertEquals("Créer La Personne", languageBusiness.findDoActionText(Crud.CREATE, Person.class,Boolean.TRUE,Boolean.TRUE));
		assertEquals("Lire Une Personne", languageBusiness.findDoActionText(Crud.READ, Person.class,Boolean.TRUE,Boolean.FALSE));
		assertEquals("Mettre à jour Les Personne", languageBusiness.findDoActionText(Crud.UPDATE, Person.class,Boolean.FALSE,Boolean.TRUE));
		assertEquals("Supprimer Des Personne", languageBusiness.findDoActionText(Crud.DELETE, Person.class,Boolean.FALSE,Boolean.FALSE));
		
		//assertEquals("Lire personne", languageBusiness.findDoActionText(Crud.READ, Person.class,Boolean.TRUE,Boolean.FALSE));
		//assertEquals("Mettre à jour personne", languageBusiness.findDoActionText(Crud.UPDATE, Person.class,Boolean.TRUE,Boolean.FALSE));
		//assertEquals("Supprimer personne", languageBusiness.findDoActionText(Crud.DELETE, Person.class,Boolean.TRUE,Boolean.FALSE));
		
		/**/
		
		assertEquals("Créer Numéro de téléphone", languageBusiness.findDoActionText(Crud.CREATE, PhoneNumber.class,Boolean.TRUE,Boolean.TRUE));
		assertEquals("Lire Numéro de téléphone", languageBusiness.findDoActionText(Crud.READ, PhoneNumber.class,Boolean.TRUE,Boolean.FALSE));
		assertEquals("Mettre à jour Numéro de téléphone", languageBusiness.findDoActionText(Crud.UPDATE, PhoneNumber.class,Boolean.FALSE,Boolean.TRUE));
		assertEquals("Supprimer Numéro de téléphone", languageBusiness.findDoActionText(Crud.DELETE, PhoneNumber.class,Boolean.FALSE,Boolean.FALSE));
		
		//assertEquals("Lire personne", languageBusiness.findDoActionText(Crud.READ, PhoneNumber.class,Boolean.TRUE,Boolean.FALSE));
		//assertEquals("Mettre à jour personne", languageBusiness.findDoActionText(Crud.UPDATE, PhoneNumber.class,Boolean.TRUE,Boolean.FALSE));
		//assertEquals("Supprimer personne", languageBusiness.findDoActionText(Crud.DELETE, PhoneNumber.class,Boolean.TRUE,Boolean.FALSE));
	}
	
	//@Test
	public void performanceNoCache(){
		for(long i=0;i<performanceMaximumNumberOfCall;i++)
			languageBusiness.findText(Locale.FRENCH, "myperformancephrase", new Object[]{"This","is","you"});
		
	}
	
	@Test
	public void performanceWithCache(){
		languageBusiness.setCachingEnabled(Boolean.TRUE);
		for(long i=0;i<performanceMaximumNumberOfCall;i++)
			languageBusiness.findText(Locale.FRENCH, "myperformancephrase", new Object[]{"This","is","you"});
		
	}

    /**/
    
    public static class MyClass{
    	
    	@Input private String user;
    	@Input private String userQuantity;
    	@Input private String userPrice;
    	@Input private String userUnitPrice;
    	@Input private String userCount;
    	@Input private String color;
    	@Input private String color2;
    	
    }

	
}
