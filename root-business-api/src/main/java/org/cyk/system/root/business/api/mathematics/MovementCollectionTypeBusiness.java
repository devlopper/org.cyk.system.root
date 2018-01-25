package org.cyk.system.root.business.api.mathematics;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeTypeBusiness;
import org.cyk.system.root.model.mathematics.MovementCollectionType;

public interface MovementCollectionTypeBusiness extends AbstractDataTreeTypeBusiness<MovementCollectionType> {

	MovementCollectionType instanciateOne(String code, String name,String incrementActionName, String decrementActionName);
	
}