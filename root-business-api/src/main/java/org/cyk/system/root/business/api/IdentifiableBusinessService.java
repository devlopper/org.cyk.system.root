package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

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

}
