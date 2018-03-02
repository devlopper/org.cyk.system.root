package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;
import org.cyk.utility.common.helper.FilterHelper;

public interface PersistenceService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> {
	
    /* Create */    IDENTIFIABLE create(IDENTIFIABLE object);
    
    /* Read */      //IDENTIFIABLE read(Class<? extends IDENTIFIABLE> aClass,IDENTIFIER identifier);
    
    /* Update */    IDENTIFIABLE update(IDENTIFIABLE object);
    
    /* Delete */    IDENTIFIABLE delete(IDENTIFIABLE object);
    
    /* exists */    Boolean exist(IDENTIFIABLE anIdentifiable);
    
    
	/* select */		PersistenceService<IDENTIFIABLE,IDENTIFIER> select(Function function);
	
						PersistenceService<IDENTIFIABLE,IDENTIFIER> select();
	
	/* filter */		PersistenceService<IDENTIFIABLE,IDENTIFIER> where(LogicalOperator aLogicalOperator,String anAttributeName,String aVarName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
						PersistenceService<IDENTIFIABLE,IDENTIFIER> where(LogicalOperator aLogicalOperator,String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
						PersistenceService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
						
						PersistenceService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);
						
	/* read all*/		Collection<IDENTIFIABLE> all();
	
	/* read one*/		<RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType);	
	
						IDENTIFIABLE one();	
						
						IDENTIFIABLE read(IDENTIFIER identifier);
						IDENTIFIABLE readByGlobalIdentifier(GlobalIdentifier globalIdentifier);
						/**
						 * Read the global identifier having this code
						 * @param code of the global identifier
						 * @return the identifiable
						 */
						IDENTIFIABLE readByGlobalIdentifierCode(String code);
						
						/**
						 * Read the globals identifiers having their code in this codes
						 * @param codes of the globals identifiers
						 * @return the identifiable collection
						 */
						Collection<IDENTIFIABLE> readByGlobalIdentifierCodes(Collection<String> codes);
						
						IDENTIFIABLE read(String globalIdentifierCode);
						
						Collection<IDENTIFIABLE> read(Collection<String> globalIdentifierCodes);
						
						Collection<IDENTIFIABLE> readByGlobalIdentifierSearchCriteria(GlobalIdentifier.SearchCriteria globalIdentifierSearchCriteria);
					    Long countByGlobalIdentifierSearchCriteria(GlobalIdentifier.SearchCriteria globalIdentifierSearchCriteria);
						
						Collection<IDENTIFIABLE> readByClosed(Boolean closed);
						Long countByClosed(Boolean closed);
					    
					    /*Collection<IDENTIFIABLE> readWhereExistencePeriodFromDateIsLessThan(Date date);
	    				Long countWhereExistencePeriodFromDateIsLessThan(Date date);*/
					    IDENTIFIABLE readFirstWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable);
	    				Collection<IDENTIFIABLE> readWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable);
	    				Long countWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable);
	    				
	    				/*Collection<IDENTIFIABLE> readWhereExistencePeriodFromDateIsGreaterThan(Date date);
	    				Long countWhereExistencePeriodFromDateIsGreaterThan(Date date);*/
	    				Collection<IDENTIFIABLE> readWhereExistencePeriodFromDateIsGreaterThan(IDENTIFIABLE identifiable);
	    				Long countWhereExistencePeriodFromDateIsGreaterThan(IDENTIFIABLE identifiable);
	    					    					    				
						Long oneLong();	
	
	/* query */			String getQueryString();
	
	                    DataReadConfiguration getDataReadConfig();
	                    
	                    void clear();
	                    
	                    void detach(IDENTIFIABLE identifiable);
	                    
	                    void flush();
	                    
	                    IDENTIFIER readOneIdentifierRandomly();
	                    Collection<IDENTIFIER> readManyIdentifiersRandomly(Integer count);
	                    Collection<IDENTIFIER> readAllIdentifiers();
	                    
	                    IDENTIFIABLE readOneRandomly();
	                    Collection<IDENTIFIABLE> readManyRandomly(Integer count);
	                    Collection<IDENTIFIABLE> readByIdentifiers(Collection<IDENTIFIER> identifiers);
	                    
	                    <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Collection<IDENTIFIABLE> readBySearchCriteria(SEARCH_CRITERIA searchCriteria);
	                	<SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Long countBySearchCriteria(SEARCH_CRITERIA searchCriteria);
	                	
	                	Collection<IDENTIFIABLE> readByFilter(FilterHelper.Filter<IDENTIFIABLE> filter,DataReadConfiguration dataReadConfiguration);
	    				Long countByFilter(FilterHelper.Filter<IDENTIFIABLE> filter,DataReadConfiguration dataReadConfiguration);
	                    
	                	
}
