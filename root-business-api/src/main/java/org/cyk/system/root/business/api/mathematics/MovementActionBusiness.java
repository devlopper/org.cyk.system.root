package org.cyk.system.root.business.api.mathematics;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.mathematics.MovementAction;

public interface MovementActionBusiness extends TypedBusiness<MovementAction> {
   
	MovementAction instanciate(String code,String name);
    
}
