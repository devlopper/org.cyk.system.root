package org.cyk.system.root.business.api.mathematics.movement;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeTypeBusiness;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;

public interface MovementCollectionTypeBusiness extends AbstractDataTreeTypeBusiness<MovementCollectionType> {

	MovementCollectionType instanciateOne(String code, String name,String incrementActionName, String decrementActionName);
	
}