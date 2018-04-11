package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionTypeModeBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionTypeMode;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionTypeModeDao;

public class MovementCollectionTypeModeBusinessImpl extends AbstractTypedBusinessService<MovementCollectionTypeMode, MovementCollectionTypeModeDao> implements MovementCollectionTypeModeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementCollectionTypeModeBusinessImpl(MovementCollectionTypeModeDao dao) {
		super(dao); 
	}
	
	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<MovementCollectionTypeMode> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementCollectionTypeMode.class);
			addParameterArrayElementString(0, MovementCollectionTypeMode.FIELD_TYPE);
			addParameterArrayElementString(1, MovementCollectionTypeMode.FIELD_MODE);
		}
		
	}
}
