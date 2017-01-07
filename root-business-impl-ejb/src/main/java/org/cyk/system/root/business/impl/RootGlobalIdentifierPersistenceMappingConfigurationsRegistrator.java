package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;

import javax.persistence.Column;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration.Property;
import org.cyk.utility.common.computation.DataReadConfiguration;

public class RootGlobalIdentifierPersistenceMappingConfigurationsRegistrator extends AbstractGlobalIdentifierPersistenceMappingConfigurationsRegistrator implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void register() {
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
        
        BusinessServiceProvider.Identifiable.COLLECTION.add(new BusinessServiceProvider.Identifiable.Adapter.Default<Person>(Person.class){
			private static final long serialVersionUID = 1322416788278558869L;
			@Override
			public Collection<Person> find(DataReadConfiguration configuration) {
				Person.SearchCriteria criteria = new Person.SearchCriteria(configuration.getGlobalFilter());
				criteria.getReadConfig().set(configuration);
				return inject(PersonBusiness.class).findByCriteria(criteria);
			}
			
			@Override
			public Long count(DataReadConfiguration configuration) {
				return inject(PersonBusiness.class).countByCriteria(new Person.SearchCriteria(configuration.getGlobalFilter()));
			}
        });
	}

}