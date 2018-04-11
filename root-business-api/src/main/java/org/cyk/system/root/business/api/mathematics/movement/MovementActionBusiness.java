package org.cyk.system.root.business.api.mathematics.movement;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.mathematics.movement.MovementAction;

public interface MovementActionBusiness extends AbstractEnumerationBusiness<MovementAction> {
   
	MovementAction instanciateOne(String code,String name);

	BigDecimal computeValue(String movementActionCode, BigDecimal value,BigDecimal increment);
	BigDecimal computeValue(MovementAction movementAction, BigDecimal value,BigDecimal increment);
    
}
