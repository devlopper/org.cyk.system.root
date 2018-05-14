package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementGroupBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementGroupItemBusiness;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.model.mathematics.movement.MovementGroupType;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementGroupDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementGroupItemDao;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.MethodHelper;

public class MovementGroupBusinessImpl extends AbstractMovementCollectionsBusinessImpl<MovementGroup,MovementGroupItem,MovementGroupDao,MovementGroupItemDao,MovementGroupItemBusiness> implements MovementGroupBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class, Boolean.FALSE);
	}
	
	@Inject
    public MovementGroupBusinessImpl(MovementGroupDao dao) {
        super(dao);
    } 
	
	@Override
	protected String getMovementCollectionFieldName() {
		return FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_COLLECTION);
	}
	
	@Override
	public MovementGroup instanciateOne() {
		return super.instanciateOne().setType(InstanceHelper.getInstance().getDefaultUsingBusinessIdentifier(MovementGroupType.class));
	}
	
	/**/
	
	public static interface Listener {
		
		Collection<MovementCollection> findMovementCollectionByParty(Party party);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Collection<MovementCollection> findMovementCollectionByParty(Party party) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Collection<MovementCollection> findMovementCollectionByParty(Party party) {
					return MethodHelper.getInstance().callGet(inject(MovementCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(party)
							, MovementCollection.class, MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION);
				}				
			}
		}
	}
}
