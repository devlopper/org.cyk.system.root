package org.cyk.system.root.business.impl.unit;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindClassLabelTextParameters;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.ApplicationAccount;
import org.cyk.system.root.model.security.UserAccount;
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
	    assertTrue("Bonjour".equals(languageBusiness.findText( Locale.FRENCH,"good.morning")));
        assertTrue("Good morning".equals(languageBusiness.findText(Locale.ENGLISH,"good.morning")));
        
        assertTrue("##hello##".equals(languageBusiness.findText( Locale.FRENCH,"hello")));
        assertTrue("##hello##".equals(languageBusiness.findText(Locale.ENGLISH,"hello")));
        
        assertEquals("Oui you are or non",languageBusiness.findText("myyes"));
	}

	@Test
    public void findFieldLabelText() {
		assertEquals("Nombre de utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "userCount", Boolean.TRUE)));
	    assertEquals("Utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "user", Boolean.TRUE))); 
	    assertEquals("Quantite de utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "userQuantity", Boolean.TRUE))); 
	    assertEquals("Prix de utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "userPrice", Boolean.TRUE))); 
	    assertEquals("Prix unitaire de utilisateur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "userUnitPrice", Boolean.TRUE))); 
	    assertEquals("Couleur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "color", Boolean.TRUE)));
	    assertEquals("Une autre couleur",languageBusiness.findFieldLabelText(FieldUtils.getDeclaredField(MyClass.class, "color2", Boolean.TRUE)));
    }
	
	@Test
    public void findClassLabelText() {
		assertEquals("Compte utilisateur",languageBusiness.findClassLabelText(UserAccount.class));
		assertEquals("Comptes utilisateurs",languageBusiness.findClassLabelText(new FindClassLabelTextParameters(UserAccount.class, Boolean.FALSE)));
		assertEquals("Compte administrateur",languageBusiness.findClassLabelText(ApplicationAccount.class));
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
    public void findDoSomethingText() {
		FindDoSomethingTextParameters parameters = new FindDoSomethingTextParameters();
		parameters.setActionIdentifier(CommonBusinessAction.CREATE);
		parameters.setSubjectClass(Person.class);
		parameters.setOne(Boolean.TRUE);
		parameters.setGlobal(Boolean.TRUE);
		parameters.setVerb(Boolean.TRUE);
		assertEquals("Créer la personne", languageBusiness.findDoSomethingText(parameters));
		parameters.setVerb(Boolean.FALSE);
		assertEquals("Création de la personne", languageBusiness.findDoSomethingText(parameters));
		parameters.setGlobal(Boolean.FALSE);
		assertEquals("Création d'une personne", languageBusiness.findDoSomethingText(parameters));
		
		parameters.setActionIdentifier(CommonBusinessAction.READ);
		parameters.setOne(Boolean.TRUE);
		parameters.setGlobal(Boolean.FALSE);
		parameters.setVerb(Boolean.TRUE);
		assertEquals("Lire une personne", languageBusiness.findDoSomethingText(parameters));
		
		parameters.setActionIdentifier(CommonBusinessAction.READ);
		parameters.setOne(Boolean.FALSE);
		parameters.setGlobal(Boolean.FALSE);
		parameters.setVerb(Boolean.TRUE);
		assertEquals("Lire des personnes", languageBusiness.findDoSomethingText(parameters));
		
		parameters.setActionIdentifier(CommonBusinessAction.UPDATE);
		parameters.setOne(Boolean.FALSE);
		parameters.setGlobal(Boolean.TRUE);
		parameters.setVerb(Boolean.TRUE);
		assertEquals("Mettre à jour les personnes", languageBusiness.findDoSomethingText(parameters));
		
		parameters.setActionIdentifier(CommonBusinessAction.DELETE);
		parameters.setOne(Boolean.FALSE);
		parameters.setGlobal(Boolean.FALSE);
		parameters.setVerb(Boolean.TRUE);
		assertEquals("Supprimer des personnes", languageBusiness.findDoSomethingText(parameters));
		
		//assertEquals("Lire personne", languageBusiness.findDoSomethingText(Crud.READ, Person.class,Boolean.TRUE,Boolean.FALSE));
		//assertEquals("Mettre à jour personne", languageBusiness.findDoSomethingText(Crud.UPDATE, Person.class,Boolean.TRUE,Boolean.FALSE));
		//assertEquals("Supprimer personne", languageBusiness.findDoSomethingText(Crud.DELETE, Person.class,Boolean.TRUE,Boolean.FALSE));
		
		/**/
		parameters.setActionIdentifier(CommonBusinessAction.CREATE);
		parameters.setSubjectClass(PhoneNumber.class);
		parameters.setOne(Boolean.TRUE);
		parameters.setGlobal(Boolean.TRUE);
		parameters.setVerb(Boolean.TRUE);
		assertEquals("Créer numéro de téléphone", languageBusiness.findDoSomethingText(parameters));
		
		parameters.setActionIdentifier(CommonBusinessAction.READ);
		parameters.setOne(Boolean.TRUE);
		parameters.setGlobal(Boolean.FALSE);
		parameters.setVerb(Boolean.TRUE);
		assertEquals("Lire numéro de téléphone", languageBusiness.findDoSomethingText(parameters));
		
		parameters.setActionIdentifier(CommonBusinessAction.UPDATE);
		parameters.setOne(Boolean.FALSE);
		parameters.setGlobal(Boolean.TRUE);
		parameters.setVerb(Boolean.TRUE);
		assertEquals("Mettre à jour numéro de téléphone", languageBusiness.findDoSomethingText(parameters));
		
		parameters.setActionIdentifier(CommonBusinessAction.DELETE);
		parameters.setOne(Boolean.FALSE);
		parameters.setGlobal(Boolean.FALSE);
		parameters.setVerb(Boolean.TRUE);
		assertEquals("Supprimer numéro de téléphone", languageBusiness.findDoSomethingText(parameters));
		
		//assertEquals("Lire personne", languageBusiness.findDoSomethingText(Crud.READ, PhoneNumber.class,Boolean.TRUE,Boolean.FALSE));
		//assertEquals("Mettre à jour personne", languageBusiness.findDoSomethingText(Crud.UPDATE, PhoneNumber.class,Boolean.TRUE,Boolean.FALSE));
		//assertEquals("Supprimer personne", languageBusiness.findDoSomethingText(Crud.DELETE, PhoneNumber.class,Boolean.TRUE,Boolean.FALSE));
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
