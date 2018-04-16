package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionValuesTransferTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferType;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionValuesTransferTypeDao;
 
public class MovementCollectionValuesTransferTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<MovementCollectionValuesTransferType,MovementCollectionValuesTransferTypeDao> implements MovementCollectionValuesTransferTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementCollectionValuesTransferTypeBusinessImpl(MovementCollectionValuesTransferTypeDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<MovementCollectionValuesTransferType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementCollectionValuesTransferType.class);
		}
		
	}
	
}
