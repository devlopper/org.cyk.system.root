package org.cyk.system.root.business.api;

import java.util.Collection;
import java.util.Set;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.accessor.InstanceFieldSetter;
import org.cyk.utility.common.builder.InstanceCopyBuilder;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;
import org.cyk.utility.common.file.ExcelSheetReader;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.MicrosoftExcelHelper;

public interface IdentifiableBusinessService <IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends BusinessService {
	
    /* ------------------------ Static methods ---------------------------- */
    
    /* Create */    IDENTIFIABLE create(IDENTIFIABLE identifiable);
    				IDENTIFIABLE create(IDENTIFIABLE identifiable,Collection<? extends AbstractIdentifiable> identifiables);
    				void create(Collection<IDENTIFIABLE> identifiables);
    
    /* Read */      //IDENTIFIABLE read(IDENTIFIER identifier);
    				Collection<IDENTIFIABLE> find(Collection<IDENTIFIABLE> identifiables,Collection<String> codes);
    				Collection<IDENTIFIABLE> find(Collection<IDENTIFIABLE> identifiables,String code);
    				IDENTIFIABLE findOne(Collection<IDENTIFIABLE> identifiables,String code);
    				
    				Collection<IDENTIFIABLE> findAll(); 
    			    Collection<IDENTIFIABLE> findAll(DataReadConfiguration configuration); 
    			    Long countAll();
    				
    				<SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Collection<IDENTIFIABLE> findBySearchCriteria(SEARCH_CRITERIA searchCriteria);
    				<SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Long countBySearchCriteria(SEARCH_CRITERIA searchCriteria);
    				
    				Collection<IDENTIFIABLE> findByFilter(FilterHelper.Filter<IDENTIFIABLE> filter,DataReadConfiguration dataReadConfiguration);
    				Long countByFilter(FilterHelper.Filter<IDENTIFIABLE> filter,DataReadConfiguration dataReadConfiguration);
    				
    				IDENTIFIABLE findFirstWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable);
    				IDENTIFIABLE findFirstWhereExistencePeriodFromDateIsLessThan(String code);
    				Collection<IDENTIFIABLE> findWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable);
    				Collection<IDENTIFIABLE> findWhereExistencePeriodFromDateIsLessThan(String code);
    				Long countWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable);
    				Long countWhereExistencePeriodFromDateIsLessThan(String code);
    				
    				Collection<IDENTIFIABLE> findWhereExistencePeriodFromDateIsGreaterThan(IDENTIFIABLE identifiable);
    				Collection<IDENTIFIABLE> findWhereExistencePeriodFromDateIsGreaterThan(String code);
    				Long countWhereExistencePeriodFromDateIsGreaterThan(IDENTIFIABLE identifiable);
    				Long countWhereExistencePeriodFromDateIsGreaterThan(String code);
    
    /* Update */    IDENTIFIABLE update(IDENTIFIABLE identifiable);
    				IDENTIFIABLE update(IDENTIFIABLE identifiable,Collection<? extends AbstractIdentifiable> identifiables);
    				void update(Collection<IDENTIFIABLE> identifiables);
    
    /* Delete */    IDENTIFIABLE delete(IDENTIFIABLE identifiable);
    				IDENTIFIABLE delete(IDENTIFIABLE identifiable,Collection<? extends AbstractIdentifiable> identifiables);
    				void delete(Collection<IDENTIFIABLE> identifiables);
    				/*
    				IDENTIFIABLE empty(IDENTIFIABLE identifiable);
    				IDENTIFIABLE empty(IDENTIFIABLE identifiable,Collection<? extends AbstractIdentifiable> identifiables);
    				void empty(Collection<IDENTIFIABLE> identifiables);
    				*/
    /* Save   */	IDENTIFIABLE save(IDENTIFIABLE identifiable);
    				void save(Collection<IDENTIFIABLE> identifiables);
    				
    				@Deprecated
    				void synchronize(ExcelSheetReader excelSheetReader,InstanceFieldSetter.TwoDimensionObjectArray<IDENTIFIABLE> setter);
    				
    				Collection<IDENTIFIABLE> instanciateMany(MicrosoftExcelHelper.Workbook.Sheet sheet,InstanceHelper.Builder.OneDimensionArray<IDENTIFIABLE> instanceBuilder);
    				void synchronize(MicrosoftExcelHelper.Workbook.Sheet sheet,InstanceHelper.Builder.OneDimensionArray<IDENTIFIABLE> instanceBuilder);
    				
    				Boolean isIdentified(IDENTIFIABLE identifiable);
    				Boolean isNotIdentified(IDENTIFIABLE identifiable);
    				
    				void setRelatedIdentifiables(IDENTIFIABLE identifiable,Boolean image,Set<String> relatedIdentifiableFieldNames);
    				void setRelatedIdentifiables(IDENTIFIABLE identifiable,Set<String> relatedIdentifiableFieldNames);
    				void setRelatedIdentifiables(IDENTIFIABLE identifiable,String...relatedIdentifiableFieldNames);
    				void setRelatedIdentifiables(IDENTIFIABLE identifiable,Boolean image,String...relatedIdentifiableFieldNames);
    				
    				InstanceCopyBuilder<IDENTIFIABLE> getInstanceCopyBuilder();
    				IDENTIFIABLE duplicate(IDENTIFIABLE identifiable);
    				
    				//<T> T generateFieldValue(IDENTIFIABLE identifiable,String name,Class<T> valueClass);
    				
    /* ------------------------ Dynamic methods ---------------------------- */
    
    /* --- Selection --- */
    
	/* select */	IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> find();
	
	                IDENTIFIABLE find(IDENTIFIER identifier);
	                
	                IDENTIFIABLE findByGlobalIdentifier(GlobalIdentifier globalIdentifier);
	                
	                IDENTIFIABLE findByGlobalIdentifierCode(String code);
	                Collection<IDENTIFIABLE> findByGlobalIdentifierCodes(Collection<String> codes);
	                
	                IDENTIFIABLE find(String code,Boolean throwableIfNull);
	                
	                Collection<IDENTIFIABLE> findByGlobalIdentifierSearchCriteria(GlobalIdentifier.SearchCriteria globalIdentifierSearchCriteria);
	                Long countByGlobalIdentifierSearchCriteria(GlobalIdentifier.SearchCriteria globalIdentifierSearchCriteria);
	
					IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> find(Function function);
	
	/* filter */	IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> where(LogicalOperator aLogicalOperator,String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
					IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
					IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);
	
	/* read all*/	Collection<IDENTIFIABLE> all();

	/* read one*/	<RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType);	

					IDENTIFIABLE one();	
	
					Long oneLong();	
					
					//DataReadConfig getDataReadConfig();
					
					IDENTIFIER findOneIdentifierRandomly();
                    Collection<IDENTIFIER> findManyIdentifiersRandomly(Integer count);
                    Collection<IDENTIFIER> findAllIdentifiers();
                    
                    IDENTIFIABLE findOneRandomly();
                    Collection<IDENTIFIABLE> findManyRandomly(Integer count);
                    Collection<IDENTIFIABLE> findByIdentifiers(Collection<IDENTIFIER> identifiers);
                    
                    Collection<IDENTIFIABLE> findByString(String string,Collection<IDENTIFIABLE> excludedIdentifiables,DataReadConfiguration dataReadConfiguration);
                    Collection<IDENTIFIABLE> findByString(String string,Collection<IDENTIFIABLE> excludedIdentifiables);
                    Collection<IDENTIFIABLE> findByString(StringSearchCriteria stringSearchCriteria,DataReadConfiguration dataReadConfiguration);
                    Collection<IDENTIFIABLE> findByString(StringSearchCriteria stringSearchCriteria);
                    
                    Long countByString(String string,Collection<IDENTIFIABLE> excludedIdentifiables);
                    Long countByString(String string);
                    Long countByString(StringSearchCriteria stringSearchCriteria);
                    
                    /**/
                    
                    Class<IDENTIFIABLE> getClazz();
                    IDENTIFIABLE instanciateOne();
                    IDENTIFIABLE instanciateOne(UserAccount userAccount);
                    IDENTIFIABLE instanciateOne(UserAccount userAccount,IDENTIFIABLE copy);
                    IDENTIFIABLE instanciateOne(ObjectFieldValues arguments);
                    
                    Collection<IDENTIFIABLE> instanciateMany(Collection<ObjectFieldValues> arguments);
                    
                    AbstractIdentifiable findParent(IDENTIFIABLE identifiable);
                    Collection<AbstractIdentifiable> findParentRecursively(IDENTIFIABLE identifiable);
                    
                    void setParents(IDENTIFIABLE identifiable,Integer levelLimitIndex);
                    void setParents(Collection<IDENTIFIABLE> identifiables,Integer levelLimitIndex);
                    
                    void setParents(IDENTIFIABLE identifiable);
                    void setParents(Collection<IDENTIFIABLE> identifiables);
                    
                    /**/
                    
                    Collection<String> findRelatedInstanceFieldNames(IDENTIFIABLE identifiable);
                    Collection<AbstractIdentifiable> findRelatedInstances(IDENTIFIABLE identifiable,Boolean setNewValue,Object newValue);
                    Collection<AbstractIdentifiable> findRelatedInstances(IDENTIFIABLE identifiable);
                    
                    void computeChanges(IDENTIFIABLE identifiable);

}
