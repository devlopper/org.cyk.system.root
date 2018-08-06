package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryType;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryTypeDao;
import org.cyk.utility.common.helper.FieldHelper;
 
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
			addParameterArrayElementString(8, MovementCollectionType.FIELD_IDENTIFIABLE_PERIOD_COLLECTION_TYPE);
			addParameterArrayElementString(9, FieldHelper.getInstance().buildPath(MovementCollectionType.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_DEFAULTED));
			
			addParameterArrayElementString(11, MovementCollectionType.FIELD_AUTOMATICALLY_JOIN_IDENTIFIABLE_PERIOD_COLLECTION);
			addParameterArrayElementString(12, MovementCollectionType.FIELD_VALUE_IS_AGGREGATED);
		}
		
	}
	
}
