package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementGroupBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementGroupItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementGroupDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementGroupItemDao;

public class MovementGroupBusinessImpl extends AbstractCollectionBusinessImpl<MovementGroup,MovementGroupItem,MovementGroupDao,MovementGroupItemDao,MovementGroupItemBusiness> implements MovementGroupBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementGroupBusinessImpl(MovementGroupDao dao) {
        super(dao);
    } 
	
}
