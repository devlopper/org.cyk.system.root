package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryType;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryTypeDao;
 
public class MovementCollectionInventoryTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<MovementCollectionInventoryType,MovementCollectionInventoryTypeDao> implements MovementCollectionInventoryTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementCollectionInventoryTypeBusinessImpl(MovementCollectionInventoryTypeDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<MovementCollectionInventoryType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementCollectionInventoryType.class);
		}
		
	}
	
}
