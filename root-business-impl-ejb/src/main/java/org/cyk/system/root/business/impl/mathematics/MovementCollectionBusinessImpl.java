package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;

@Stateless
public class MovementCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MovementCollection,Movement, MovementCollectionDao,MovementDao> implements MovementCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private MovementDao movementDao;
	
	@Inject
	public MovementCollectionBusinessImpl(MovementCollectionDao dao) {
		super(dao); 
	}
		
	@Override
	protected MovementDao getItemDao() {
		return movementDao;
	}
	
	@Override
	public MovementCollection create(MovementCollection movementCollection) {
		RootBusinessLayer.getInstance().getIntervalBusiness().create(movementCollection.getInterval());
		RootBusinessLayer.getInstance().getMovementActionBusiness().create(movementCollection.getIncrementAction());
		RootBusinessLayer.getInstance().getMovementActionBusiness().create(movementCollection.getDecrementAction());
		return super.create(movementCollection);
	}

}
