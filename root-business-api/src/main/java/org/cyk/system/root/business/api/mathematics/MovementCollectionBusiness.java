package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;

public interface MovementCollectionBusiness extends AbstractCollectionBusiness<MovementCollection,Movement> {

	MovementCollection instanciateOne(String code,String incrementActionName,String decrementActionName);
	MovementCollection instanciateOne(String code,String intervalCode,String incrementActionCode,String decrementActionCode);
	
	BigDecimal computeValue(MovementCollection movementCollection,MovementAction movementAction,BigDecimal increment);
	
}
