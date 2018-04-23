package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementReasonBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementReason;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementReasonDao;
 
public class MovementReasonBusinessImpl extends AbstractDataTreeTypeBusinessImpl<MovementReason,MovementReasonDao> implements MovementReasonBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementReasonBusinessImpl(MovementReasonDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<MovementReason> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementReason.class);
		}
		
	}
	
}
