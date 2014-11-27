package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessException;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.file.TagBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.business.api.party.PersonBusiness;
import org.cyk.system.root.business.api.security.PermissionBusiness;
import org.cyk.system.root.business.impl.validation.FieldValidatorMethod;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.model.file.Tag;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.party.MaritalStatus;
import org.cyk.system.root.model.party.Person;
import org.cyk.system.root.model.party.Sex;
import org.cyk.system.root.model.security.Administrator;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Manager;
import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.Role;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Deployment(initialisationType=InitialisationType.EAGER)
public class RootBusinessLayer extends AbstractBusinessLayer implements Serializable {
 
	private static final long serialVersionUID = 4576531258594638L;
	@Inject private LocalityBusiness localityBusiness;
	@Inject private LocalityTypeBusiness localityTypeBusiness;
	@Inject private TagBusiness tagBusiness;
    @Inject private EventBusiness eventBusiness;
    @Inject private PersonBusiness personBusiness;
    @Inject private PermissionBusiness permissionBusiness;
    
    //private Person personAdmin,personGuest;
    
    /* Validators */
   // @Inject private PersonValidator personValidator;
    
    @Override
    protected void initialisation() {
        super.initialisation();
        //registerValidator(Person.class, personValidator);
        
        registerFieldValidator(commonUtils.getFieldFromClass(Person.class, "name"), new FieldValidatorMethod() {
            
			private static final long serialVersionUID = 1L;

			@Override
            protected Void __execute__(Object[] parameter) {
                String name = (String) parameter[0];
                if(!"cj".equals(name))
                    throw new BusinessException("Method exception oohhh");
                return null;
            }
        });
        
    }
    
    @Override
    public void createInitialData() {
        geography();
        language();
        party();
        security();
    }
    
    private void geography(){
        LocalityType continent=new LocalityType(null, "CONTINENT", "Continent");
        create(continent);
        LocalityType country=new LocalityType(continent, "COUNTRY", "Country");
        create(country);
        LocalityType city=new LocalityType(country, "CITY", "City");
        create(city);
        
        Locality afrique;
        
        create(afrique = new Locality(null, continent, "Afrique"));
        create(new Locality(null, continent, "Amerique"));
        create(new Locality(null, continent, "Europe"));
        
        create(new Locality(afrique, continent, "Cote d'Ivoire"));
        create(new Locality(afrique, continent, "Benin"));
        
        create(new PhoneNumberType("FIXE", "Fixe"));
        create(new PhoneNumberType("MOBILE", "Mobile"));
        
        
        
        create(new EventType("RDV", "Rendez vous", null));
        create(new EventType("REU", "Reunion", null));
        
        create(new Language("fr","Francais"));
        create(new Language("en","Anglais"));
        create(new Language("es","Espagnol"));
    }
    
    private void language(){
         
    }
    
    private void party(){
    	create(new Sex("M", "Masculin"));
        create(new Sex("F", "Feminin"));
        
        create(new MaritalStatus("B", "Celibataire"));
        create(new MaritalStatus("M", "Marie"));
        
        //personAdmin = create(new Person("CYK","System"));
        //personGuest = create(new Person("Any","One"));
    }
    
    
    private void security(){ 
    	Permission licenceRead;
    	
    	create(licenceRead = new Permission(permissionBusiness.computeCode(License.class, Crud.READ)));
    	
    	createRole(new Administrator(), licenceRead);
    	createRole(new Manager(), licenceRead);
        create(new Role(Role.REGISTERED, "Registered"));
    }
    
    private void createRole(Role role,Permission...permissions){
    	 if(permissions!=null)
    		 for(Permission permission : permissions)
    			 role.getPermissions().add(permission);
    	 create(role);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> beansMap) {
        beansMap.put((Class)Event.class, (TypedBusiness)eventBusiness);
        beansMap.put((Class)Person.class, (TypedBusiness)personBusiness);
        beansMap.put((Class)Locality.class, (TypedBusiness)localityBusiness);
        beansMap.put((Class)LocalityType.class, (TypedBusiness)localityTypeBusiness);
        beansMap.put((Class)Tag.class, (TypedBusiness)tagBusiness);
    }

}
