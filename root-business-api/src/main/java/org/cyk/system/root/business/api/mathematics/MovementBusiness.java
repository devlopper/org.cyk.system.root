package org.cyk.system.root.business.api.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;

import lombok.Getter;
import lombok.Setter;

public interface MovementBusiness extends AbstractCollectionItemBusiness<Movement,MovementCollection> {

	Movement instanciateOne(MovementCollection movementCollection,MovementAction movementAction, String value);
	
	@Getter @Setter
	public static class CompleteMovementInstanciationOfOneFromValuesArguments extends AbstractCompleteInstanciationOfOneFromValuesArguments<Movement> implements Serializable{

		private static final long serialVersionUID = 6568108456054174796L;
		
		private Integer movementCollectionCodeIndex,value;
		
		public void setValues(String[] values){
			this.values = values;
			
		}
	}
	
	@Getter @Setter
	public static class CompleteMovementInstanciationOfManyFromValuesArguments extends AbstractCompleteInstanciationOfManyFromValuesArguments<Movement> implements Serializable{

		private static final long serialVersionUID = 6568108456054174796L;
		
		private CompleteMovementInstanciationOfOneFromValuesArguments instanciationOfOneFromValuesArguments = new CompleteMovementInstanciationOfOneFromValuesArguments();
		
	}
}
