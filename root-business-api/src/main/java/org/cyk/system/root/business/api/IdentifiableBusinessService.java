package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;
import org.cyk.utility.common.file.ExcelSheetReader;

import lombok.Getter;
import lombok.Setter;

public interface IdentifiableBusinessService <IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends BusinessService {
	
    /* ------------------------ Static methods ---------------------------- */
    
    /* Create */    IDENTIFIABLE create(IDENTIFIABLE identifiable);
    				IDENTIFIABLE create(IDENTIFIABLE identifiable,Collection<? extends AbstractIdentifiable> identifiables);
    				void create(Collection<IDENTIFIABLE> identifiables);
    
    /* Read */      //IDENTIFIABLE read(IDENTIFIER identifier);
    				Collection<IDENTIFIABLE> find(Collection<IDENTIFIABLE> identifiables,Collection<String> codes);
    				Collection<IDENTIFIABLE> find(Collection<IDENTIFIABLE> identifiables,String code);
    				IDENTIFIABLE findOne(Collection<IDENTIFIABLE> identifiables,String code);
    
    /* Update */    IDENTIFIABLE update(IDENTIFIABLE identifiable);
    				IDENTIFIABLE update(IDENTIFIABLE identifiable,Collection<? extends AbstractIdentifiable> identifiables);
    				void update(Collection<IDENTIFIABLE> identifiables);
    
    /* Delete */    IDENTIFIABLE delete(IDENTIFIABLE identifiable);
    				IDENTIFIABLE delete(IDENTIFIABLE identifiable,Collection<? extends AbstractIdentifiable> identifiables);
    				void delete(Collection<IDENTIFIABLE> identifiables);
    
    /* ------------------------ Dynamic methods ---------------------------- */
    
    /* --- Selection --- */
    
	/* select */	IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> find();
	
	                IDENTIFIABLE find(IDENTIFIER identifier);
	                
	                IDENTIFIABLE findByGlobalIdentifier(GlobalIdentifier globalIdentifier);
	                
	                IDENTIFIABLE findByGlobalIdentifierCode(String code);
	                Collection<IDENTIFIABLE> findByGlobalIdentifierCodes(Collection<String> codes);
	                
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
                    IDENTIFIABLE instanciateOne(ObjectFieldValues arguments);
                    void completeInstanciationOfOne(IDENTIFIABLE identifiable);
                    
                    Collection<IDENTIFIABLE> instanciateMany(Collection<ObjectFieldValues> arguments);
                    void completeInstanciationOfManyFromValues(List<IDENTIFIABLE> actors,AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE> arguments);
                    
                    void completeInstanciationOfOneFromValues(IDENTIFIABLE identifiable,AbstractCompleteInstanciationOfOneFromValuesArguments<IDENTIFIABLE> arguments);
                    IDENTIFIABLE completeInstanciationOfOneFromValues(AbstractCompleteInstanciationOfOneFromValuesArguments<IDENTIFIABLE> arguments);
                    
                    List<IDENTIFIABLE> completeInstanciationOfManyFromValues(AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE> arguments);
                    List<IDENTIFIABLE> instanciateMany(ExcelSheetReader excelSheetReader,AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE> completeInstanciationOfManyFromValuesArguments);
                    
                    void completeInstanciationOfMany(Collection<IDENTIFIABLE> identifiables);
                    
                    Boolean isIdentified(AbstractIdentifiable identifiable);
                	
                    Boolean isNotIdentified(AbstractIdentifiable identifiable);
                    
                    AbstractIdentifiable findParent(IDENTIFIABLE identifiable);
                    Collection<AbstractIdentifiable> findParentRecursively(IDENTIFIABLE identifiable);
                    void setParents(IDENTIFIABLE identifiable);
                    void setParents(Collection<IDENTIFIABLE> identifiables);
                    
                    /**/
                    
                	public static interface CompleteInstanciationOfOneFromValuesListener<IDENTIFIABLE extends Identifiable<?>>{

                		void beforeProcessing(IDENTIFIABLE identifiable,String[] values);
                		void afterProcessing(IDENTIFIABLE identifiable,String[] values);

                	}
                	
                	@Getter @Setter
                	public static class AbstractCompleteInstanciationOfOneFromValuesArguments<IDENTIFIABLE extends Identifiable<?>> implements Serializable{

                		private static final long serialVersionUID = 6568108456054174796L;
                		
                		protected String[] values;
                		protected CompleteInstanciationOfOneFromValuesListener<IDENTIFIABLE> listener;
                		
                	}
                	
                	public static interface CompleteInstanciationOfManyFromValuesListener<IDENTIFIABLE extends Identifiable<?>>{

                		void beforeProcessing(List<IDENTIFIABLE> identifiables,List<String[]> values);
                		void afterProcessing(List<IDENTIFIABLE> identifiables,List<String[]> values);
                		
                	}
                	
                	@Getter @Setter
                	public static class AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE extends Identifiable<?>> implements Serializable{

                		private static final long serialVersionUID = 6568108456054174796L;
                		
                		protected List<String[]> values;
                		protected CompleteInstanciationOfManyFromValuesListener<IDENTIFIABLE> listener;
                		
                	}
    

}
