package org.cyk.system.root.business.impl.__data__;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.time.IdentifiablePeriodCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptEvaluationEngine;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.information.IdentifiableCollectionType;
import org.cyk.system.root.model.information.InformationState;
import org.cyk.system.root.model.information.Tangibility;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.movement.MovementAction;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionTypeMode;
import org.cyk.system.root.model.mathematics.movement.MovementMode;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.model.network.Service;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Medication;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRoleName;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.security.BusinessServiceCollection;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.model.store.Store;
import org.cyk.system.root.model.store.StoreType;
import org.cyk.system.root.model.time.DurationType;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuLocation;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNodeType;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuRenderType;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuType;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.model.value.MeasureType;
import org.cyk.system.root.model.value.NullString;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueProperties;

public class RealDataSet extends DataSet implements Serializable {
	private static final long serialVersionUID = 1L;

	public RealDataSet() {
		super(RootBusinessLayer.class);
		file();
    	values();
    	geography();
    	store();
        event();
        time();
        language();
        party();
        security();
        
        network();
        
        message();
        mathematics();
        information();
        
        userInterface();
	}
	
	private void geography(){
    	addClass(LocalityType.class);
    	addClass(Locality.class);
        addClass(Country.class);
        addClass(PhoneNumberType.class);
        addClass(LocationType.class);
        addClass(ElectronicMailAddress.class);
    }
    
    private void language(){
    	addClass(Language.class);
    }
    
    private void event(){ 
    	addClass(NotificationTemplate.class);
    	addClass(EventMissedReason.class);
    }
    
    private void file(){ 
    	addClass(FileRepresentationType.class);
    	addClass(ScriptEvaluationEngine.class);
    	addClass(Script.class);
    }
    
    private void time(){ 
    	addClass(TimeDivisionType.class);
    	addClass(DurationType.class);
    	addClass(IdentifiablePeriodCollectionType.class);
    	addClass(IdentifiablePeriodCollection.class);
    }
    
    private void party(){
    	addClass(Sex.class);
    	addClass(MaritalStatus.class);
    	addClass(JobFunction.class);
    	addClass(JobTitle.class);
    	addClass(PersonTitle.class);
    	addClass(BloodGroup.class);
    	addClass(Allergy.class);
    	addClass(Medication.class);
    	addClass(BusinessRole.class);
    	addClass(PersonRelationshipTypeGroup.class);
    	addClass(PersonRelationshipType.class);
    	addClass(PersonRelationshipTypeRoleName.class);
    	addClass(PersonRelationshipTypeRole.class);
    	
    }
    
    private void security(){ 
    	/*createRole(RootConstant.Code.Role.ADMINISTRATOR, "Administrator");
    	createRole(RootConstant.Code.Role.MANAGER, "Manager");
        createRole(RootConstant.Code.Role.SETTING_MANAGER, "Setting Manager");
        createRole(RootConstant.Code.Role.SECURITY_MANAGER, "Security Manager");
        createRole(RootConstant.Code.Role.USER, "User",SHIRO_PRIVATE_FOLDER);
        */
        addClass(Role.class);
        addClass(Software.class);
        addClass(Credentials.class);
        addClass(BusinessServiceCollection.class);
    }
    
    private void network(){ 
    	addClass(Computer.class);
        addClass(Service.class);
    }
    
    private void message(){ 
    	addClass(SmtpProperties.class);
    }
    
    private void mathematics(){ 
    	addClass(IntervalCollection.class);
    	addClass(Interval.class);
    	addClass(MetricCollectionType.class);
    	addClass(MovementAction.class);
    	addClass(MovementMode.class);
    	addClass(MovementCollectionType.class);
    	addClass(MovementCollectionTypeMode.class);
    	addClass(MovementCollection.class);
    }
    
    private void values(){ 
    	addClass(MeasureType.class);
    	addClass(Measure.class);
    	addClass(NullString.class);
    	addClass(ValueProperties.class);
    	addClass(Value.class);
    }
    
    private void store(){ 
    	addClass(StoreType.class);
    	addClass(Store.class);	
    }
    
    private void information(){ 
    	addClass(IdentifiableCollectionType.class);
    	addClass(Tangibility.class);
    	addClass(InformationState.class);
    }
    
    private void userInterface(){ 
    	addClass(UserInterfaceMenuRenderType.class);
    	addClass(UserInterfaceMenuLocation.class);
    	addClass(UserInterfaceMenuNodeType.class);
    	addClass(UserInterfaceMenuType.class);
    }
    
    /**/
    
    @Override
    public void __save__() {
    	super.__save__();
    	IdentifiablePeriodCollectionIdentifiableGlobalIdentifier identifiablePeriodCollectionIdentifiableGlobalIdentifier = 
    			inject(IdentifiablePeriodCollectionIdentifiableGlobalIdentifierBusiness.class).instanciateOne()
    			.setIdentifiablePeriodCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY)
    			.setIdentifiableGlobalIdentifierFromCode(MovementCollection.class, RootConstant.Code.MovementCollection.CASH_REGISTER);
    	if(identifiablePeriodCollectionIdentifiableGlobalIdentifier.getIdentifiablePeriodCollection()!=null && 
    			identifiablePeriodCollectionIdentifiableGlobalIdentifier.getIdentifiableGlobalIdentifier()!=null)
    		inject(GenericBusiness.class).create(identifiablePeriodCollectionIdentifiableGlobalIdentifier);
    	
    }
    
    /**/
    
    public static final Collection<Class<?>> CLASSES_PARTIES = new ArrayList<>();
    public static final Collection<Class<?>> CLASSES_VALUES = new ArrayList<>();
    public static final Collection<Class<?>> CLASSES_MATHEMATIQUES = new ArrayList<>();
    public static final Collection<Class<?>> CLASSES_MESSAGE = new ArrayList<>();
    public static final Collection<Class<?>> CLASSES_NETWORK = new ArrayList<>();
    public static final Collection<Class<?>> CLASSES_SECURITY = new ArrayList<>();
    
    static {
    	CLASSES_VALUES.addAll(Arrays.asList(MeasureType.class,Measure.class,NullString.class,ValueProperties.class,Value.class));
    	CLASSES_MATHEMATIQUES.addAll(Arrays.asList(IntervalCollection.class,Interval.class,MetricCollectionType.class,MovementAction.class
    			,MovementMode.class,MovementCollectionType.class,MovementCollectionTypeMode.class));
    	CLASSES_MESSAGE.addAll(Arrays.asList(SmtpProperties.class));
    	CLASSES_NETWORK.addAll(Arrays.asList(Computer.class,Service.class));
    	CLASSES_SECURITY.addAll(Arrays.asList(Role.class,Software.class,Credentials.class,BusinessServiceCollection.class));
    	//CLASSES_PARTIES.addAll(Arrays.asList(Sex.class,MaritalStatus.class));
    }
}

