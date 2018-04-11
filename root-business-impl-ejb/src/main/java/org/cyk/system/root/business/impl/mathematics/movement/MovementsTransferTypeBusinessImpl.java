package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferType;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementsTransferTypeDao;
 
public class MovementsTransferTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<MovementsTransferType,MovementsTransferTypeDao> implements MovementsTransferTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementsTransferTypeBusinessImpl(MovementsTransferTypeDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<MovementsTransferType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementsTransferType.class);
		}
		
	}
	
}
