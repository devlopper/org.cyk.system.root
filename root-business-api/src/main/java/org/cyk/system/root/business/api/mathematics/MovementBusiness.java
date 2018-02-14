package org.cyk.system.root.business.api.mathematics;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;

public interface MovementBusiness extends AbstractCollectionItemBusiness<Movement,MovementCollection> {

	Movement instanciateOne(String collectionCode, String value,String supportingDocumentCode,String supportingDocumentPhysicalCreator,String supportingDocumentContentWriter,String actionCode);
	Movement instanciateOne(MovementCollection movementCollection,MovementAction movementAction, String value);
	Movement instanciateOne(String code,String collectionCode, String value,Boolean increment);
	Movement instanciateOne(String code,String collectionCode, String value);
	Movement instanciateOne(MovementCollection movementCollection,String typeCode, Crud crud, AbstractIdentifiable identifiable,String valueFieldName);
	
	Movement createIfActionIsNotNull(Movement movement);
}
