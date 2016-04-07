package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public interface PersistenceService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> {
	
    /* Create */    IDENTIFIABLE create(IDENTIFIABLE object);
    
    /* Read */      //IDENTIFIABLE read(Class<? extends IDENTIFIABLE> aClass,IDENTIFIER identifier);
    
    /* Update */    IDENTIFIABLE update(IDENTIFIABLE object);
    
    /* Delete */    IDENTIFIABLE delete(IDENTIFIABLE object);
    
    /* exists */    Boolean exist(IDENTIFIABLE anIdentifiable);
    
    
	/* select */		PersistenceService<IDENTIFIABLE,IDENTIFIER> select(Function function);
	
						PersistenceService<IDENTIFIABLE,IDENTIFIER> select();
	
	/* filter */		PersistenceService<IDENTIFIABLE,IDENTIFIER> where(LogicalOperator aLogicalOperator,String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
						PersistenceService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
						
						PersistenceService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);
						
	/* read all*/		Collection<IDENTIFIABLE> all();
	
	/* read one*/		<RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType);	
	
						IDENTIFIABLE one();	
						
						IDENTIFIABLE read(IDENTIFIER identifier);   
						
						Long oneLong();	
	
	/* query */			String getQueryString();
	
	                    DataReadConfiguration getDataReadConfig();
	                    
	                    void clear();
	                    
	                    IDENTIFIER readOneIdentifierRandomly();
	                    Collection<IDENTIFIER> readManyIdentifiersRandomly(Integer count);
	                    Collection<IDENTIFIER> readAllIdentifiers();
	                    
	                    IDENTIFIABLE readOneRandomly();
	                    Collection<IDENTIFIABLE> readManyRandomly(Integer count);
	                    Collection<IDENTIFIABLE> readByIdentifiers(Collection<IDENTIFIER> identifiers);
	                    
	                    
}
