package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementModeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementMode;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementModeDao;

public class MovementModeBusinessImpl extends AbstractEnumerationBusinessImpl<MovementMode, MovementModeDao> implements MovementModeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementModeBusinessImpl(MovementModeDao dao) {
		super(dao); 
	}
		
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<MovementMode> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementMode.class);
			addParameterArrayElementString(10, MovementMode.FIELD_SUPPORT_DOCUMENT_IDENTIFIER);
		}
		
	}
}
