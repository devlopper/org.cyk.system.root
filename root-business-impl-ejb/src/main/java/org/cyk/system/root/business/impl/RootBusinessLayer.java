package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.file.TagBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.security.PermissionBusiness;
import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Tag;
import org.cyk.system.root.model.file.report.ReportTable;
import org.cyk.system.root.model.file.report.ReportTableConfiguration;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Deployment(initialisationType=InitialisationType.EAGER)
public class RootBusinessLayer extends AbstractBusinessLayer implements Serializable {
 
	private static final long serialVersionUID = 4576531258594638L;
	
	private static RootBusinessLayer INSTANCE;
	
	@Getter private final String parameterGenericObjectReportTable = "gortb"; 
	
	@Inject private LocalityBusiness localityBusiness;
	@Inject private LocalityTypeBusiness localityTypeBusiness;
	@Inject private TagBusiness tagBusiness;
    @Inject private EventBusiness eventBusiness;
    @Inject private PersonBusiness personBusiness;
    @Inject private PermissionBusiness permissionBusiness;
    
    @Inject private PersonValidator personValidator;
    @Inject private FileValidator fileValidator;
    
    //private Person personAdmin,personGuest;
        
    @Override
    protected void initialisation() {
    	INSTANCE = this;
        super.initialisation();
        
        registerValidator(Person.class, personValidator);
        registerValidator(File.class, fileValidator);
        /*
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
        */
        
        registerReportConfiguration(new ReportTableConfiguration<Object, ReportTable<Object>>(parameterGenericObjectReportTable) {

			@Override
			public ReportTable<Object> build(Class<Object> aClass,Collection<Object> models,String fileExtension,Boolean print) {
				return reportBusiness.buildTable(aClass,models, fileExtension, print);
			}

			@Override
			public ReportTable<Object> build(Class<Object> aClass,String fileExtension, Boolean print) {
				return reportBusiness.buildTable(aClass, fileExtension, print);
			}
		});
    }
    
    @Override
    public void createInitialData() {
        geography();
        event();
        time();
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
        
        
    }
    
    private void language(){
    	create(new Language("fr","Francais"));
        create(new Language("en","Anglais"));
        create(new Language("es","Espagnol"));
    }
    
    private void event(){ 
    	create(new EventType("RDV", "Rendez vous", null));
        create(new EventType("REU", "Reunion", null));
    }
    
    private void time(){ 
    	create(new TimeDivisionType(TimeDivisionType.DAY, "Journalier",DateUtils.MILLIS_PER_DAY ,Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.WEEK, "Hebdomadaire",DateUtils.MILLIS_PER_DAY*7, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.MONTH, "Mensuel",DateUtils.MILLIS_PER_DAY*30, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.TRIMESTER, "Trimestre",DateUtils.MILLIS_PER_DAY*30*3, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.SEMESTER, "Semestre",DateUtils.MILLIS_PER_DAY*30*6, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.YEAR, "Annuel",DateUtils.MILLIS_PER_DAY*30*12,Boolean.TRUE));
        
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
    	
    	createRole(new Role(Role.ADMINISTRATOR, "Administrator"), licenceRead);
    	createRole(new Role(Role.MANAGER, "Manager"), licenceRead);
        create(new Role(Role.BUSINESS_ACTOR, "Business actor"));
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
    
    public static RootBusinessLayer getInstance() {
		return INSTANCE;
	}

}
