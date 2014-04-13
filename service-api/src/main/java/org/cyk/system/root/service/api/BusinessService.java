package org.cyk.system.root.service.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public interface BusinessService <IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> {
	
	/* select */	BusinessService<IDENTIFIABLE,IDENTIFIER> find();
	
					BusinessService<IDENTIFIABLE,IDENTIFIER> find(Function function);
	
	/* filter */	BusinessService<IDENTIFIABLE,IDENTIFIER> where(LogicalOperator aLogicalOperator,String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
					BusinessService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator);
	
					BusinessService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);
	
	/* read all*/	Collection<IDENTIFIABLE> all();

	/* read one*/	<RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType);	

					IDENTIFIABLE one();	
	
					Long oneLong();	

}
