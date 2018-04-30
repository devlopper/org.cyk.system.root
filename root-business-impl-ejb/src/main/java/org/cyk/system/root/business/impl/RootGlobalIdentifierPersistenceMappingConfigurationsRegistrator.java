package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.persistence.Column;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.userinterface.UserInterfaceCommand;
import org.cyk.system.root.model.userinterface.UserInterfaceComponent;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration.Property;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.FieldHelper;

public class RootGlobalIdentifierPersistenceMappingConfigurationsRegistrator extends AbstractGlobalIdentifierPersistenceMappingConfigurationsRegistrator implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void register() {
		//FieldHelper.Field.get(Script.class, FieldHelper.getInstance().buildPath(Script.FIELD_FILE,File.FIELD_TEXT)).getConstraints().setIsNullable(Boolean.FALSE);
		
		FieldHelper.Field.get(IdentifiablePeriod.class, IdentifiablePeriod.FIELD_COLLECTION).getConstraints().setIsNullable(Boolean.FALSE);
		FieldHelper.Field.get(IdentifiablePeriod.class, FieldHelper.getInstance().buildPath(IdentifiablePeriod.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD
				,Period.FIELD_FROM_DATE)).getConstraints().setIsNullable(Boolean.FALSE);
		FieldHelper.Field.get(IdentifiablePeriod.class, FieldHelper.getInstance().buildPath(IdentifiablePeriod.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD
				,Period.FIELD_TO_DATE)).getConstraints().setIsNullable(Boolean.FALSE);
		FieldHelper.Field.get(IdentifiablePeriod.class, FieldHelper.getInstance().buildPath(IdentifiablePeriod.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CLOSED))
			.getConstraints().setIsNullable(Boolean.FALSE);
		FieldHelper.Field.get(IdentifiablePeriod.class, FieldHelper.getInstance().buildPath(IdentifiablePeriod.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD
				,Period.FIELD_FROM_DATE)).getConstraints().setDatePart(Constant.Date.Part.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND);
		FieldHelper.Field.get(IdentifiablePeriod.class, FieldHelper.getInstance().buildPath(IdentifiablePeriod.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD
				,Period.FIELD_TO_DATE)).getConstraints().setDatePart(Constant.Date.Part.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND);
		
		FieldHelper.Field.get(MovementCollection.class, FieldHelper.getInstance().buildPath(MovementCollection.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE
				)).getConstraints().setIsNullable(Boolean.FALSE).setIsUnique(Boolean.TRUE);
		
		FieldHelper.Field.get(Movement.class, Movement.FIELD_COLLECTION).getConstraints().setIsNullable(Boolean.FALSE);
		FieldHelper.Field.get(Movement.class, FieldHelper.getInstance().buildPath(Movement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD
				,Period.FIELD_FROM_DATE)).getConstraints().setDatePart(Constant.Date.Part.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND);
		
		FieldHelper.Field.get(MovementCollectionValuesTransferItemCollection.class, FieldHelper.getInstance().buildPath(MovementCollectionValuesTransfer.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE
				)).getConstraints().setIsNullable(Boolean.FALSE).setIsUnique(Boolean.TRUE);
		FieldHelper.Field.get(MovementCollectionValuesTransfer.class, FieldHelper.getInstance().buildPath(MovementCollectionValuesTransfer.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE
				)).getConstraints().setIsNullable(Boolean.FALSE).setIsUnique(Boolean.TRUE);
		FieldHelper.Field.get(MovementCollectionValuesTransferAcknowledgement.class, FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferAcknowledgement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE
				)).getConstraints().setIsNullable(Boolean.FALSE).setIsUnique(Boolean.TRUE);
		FieldHelper.Field.get(MovementCollectionInventory.class, FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferAcknowledgement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE
				)).getConstraints().setIsNullable(Boolean.FALSE).setIsUnique(Boolean.TRUE);
		
		GlobalIdentifierPersistenceMappingConfiguration configuration = new GlobalIdentifierPersistenceMappingConfiguration();
        Property property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.TRUE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return false;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        GlobalIdentifierPersistenceMappingConfiguration.register(Person.class, configuration);
        
        configuration = new GlobalIdentifierPersistenceMappingConfiguration();
        property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.TRUE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return false;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        GlobalIdentifierPersistenceMappingConfiguration.register(UniformResourceLocator.class, configuration);
        
        configuration = new GlobalIdentifierPersistenceMappingConfiguration();
        property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.TRUE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return false;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_NAME),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.FALSE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return Boolean.TRUE;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        GlobalIdentifierPersistenceMappingConfiguration.register(UniformResourceLocatorParameter.class, configuration);
        
        configuration = new GlobalIdentifierPersistenceMappingConfiguration();
        property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.TRUE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return false;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        GlobalIdentifierPersistenceMappingConfiguration.register(PersonRelationship.class, configuration);
        
        configuration = new GlobalIdentifierPersistenceMappingConfiguration();
        property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.TRUE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return false;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        GlobalIdentifierPersistenceMappingConfiguration.register(UserInterfaceComponent.class, configuration);
        
        configuration = new GlobalIdentifierPersistenceMappingConfiguration();
        property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.TRUE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return false;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        GlobalIdentifierPersistenceMappingConfiguration.register(UserInterfaceCommand.class, configuration);
        
        configuration = new GlobalIdentifierPersistenceMappingConfiguration();
        property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.TRUE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return false;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        GlobalIdentifierPersistenceMappingConfiguration.register(JobTitle.class, configuration);
        
        configuration = new GlobalIdentifierPersistenceMappingConfiguration();
        property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.TRUE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return false;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        GlobalIdentifierPersistenceMappingConfiguration.register(Locality.class, configuration);
        
	}

}
