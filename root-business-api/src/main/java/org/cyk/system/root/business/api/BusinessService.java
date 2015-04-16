package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfig;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public interface BusinessService <IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> {
	
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
    
	/* select */	BusinessService<IDENTIFIABLE,IDENTIFIER> find();
	
	                IDENTIFIABLE find(IDENTIFIER identifier);
	
					BusinessService<IDENTIFIABLE,IDENTIFIER> find(Function function);
	
	/* filter */	BusinessService<IDENTIFIABLE,IDENTIFIER> where(LogicalOperator aLogicalOperator,String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
					BusinessService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
					BusinessService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);
	
	/* read all*/	Collection<IDENTIFIABLE> all();

	/* read one*/	<RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType);	

					IDENTIFIABLE one();	
	
					Long oneLong();	
					
					DataReadConfig getDataReadConfig();

}
