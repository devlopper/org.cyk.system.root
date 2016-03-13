package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.system.root.model.Identifiable;
import org.cyk.utility.common.CommonUtils.ReadExcelSheetArguments;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

import lombok.Getter;
import lombok.Setter;

public interface IdentifiableBusinessService <IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends BusinessService {
	
    /* ------------------------ Static methods ---------------------------- */
    
    /* Create */    IDENTIFIABLE create(IDENTIFIABLE identifiable);
    				void create(Collection<IDENTIFIABLE> identifiables);
    
    /* Read */      //IDENTIFIABLE read(IDENTIFIER identifier);
    
    /* Update */    IDENTIFIABLE update(IDENTIFIABLE identifiable);
    				void update(Collection<IDENTIFIABLE> identifiables);
    
    /* Delete */    IDENTIFIABLE delete(IDENTIFIABLE identifiable);
    				void delete(Collection<IDENTIFIABLE> identifiables);
    
    /* ------------------------ Dynamic methods ---------------------------- */
    
    /* --- Selection --- */
    
	/* select */	IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> find();
	
	                IDENTIFIABLE find(IDENTIFIER identifier);
	
					IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> find(Function function);
	
	/* filter */	IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> where(LogicalOperator aLogicalOperator,String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
					IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
					IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);
	
	/* read all*/	Collection<IDENTIFIABLE> all();

	/* read one*/	<RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType);	

					IDENTIFIABLE one();	
	
					Long oneLong();	
					
					//DataReadConfig getDataReadConfig();
					
					Long findOneIdentifierRandomly();
                    Collection<Long> findManyIdentifiersRandomly(Integer count);
                    Collection<Long> findAllIdentifiers();
                    
                    IDENTIFIABLE findOneRandomly();
                    Collection<IDENTIFIABLE> findManyRandomly(Integer count);
                    
                    /**/
                    
                    Class<IDENTIFIABLE> getClazz();
                    IDENTIFIABLE instanciateOne();
                    IDENTIFIABLE instanciateOne(ObjectFieldValues arguments);
                    void completeInstanciationOfOne(IDENTIFIABLE identifiable);
                    
                    Collection<IDENTIFIABLE> instanciateMany(Collection<ObjectFieldValues> arguments);
                    void completeInstanciationOfManyFromValues(List<IDENTIFIABLE> actors,AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE> arguments);
                    
                    void completeInstanciationOfOneFromValues(IDENTIFIABLE identifiable,AbstractCompleteInstanciationOfOneFromValuesArguments<IDENTIFIABLE> arguments);
                    IDENTIFIABLE completeInstanciationOfOneFromValues(AbstractCompleteInstanciationOfOneFromValuesArguments<IDENTIFIABLE> arguments);
                    
                    List<IDENTIFIABLE> completeInstanciationOfManyFromValues(AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE> arguments);
                    List<IDENTIFIABLE> instanciateMany(ReadExcelSheetArguments readExcelSheetArguments,AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE> completeInstanciationOfManyFromValuesArguments);
                    
                    void completeInstanciationOfMany(Collection<IDENTIFIABLE> identifiables);
                    
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
