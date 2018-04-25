package org.cyk.system.root.persistence.impl.mathematics.movement;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementGroupDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class MovementGroupDaoImpl extends AbstractCollectionDaoImpl<MovementGroup,MovementGroupItem> implements MovementGroupDao,Serializable {
	private static final long serialVersionUID = 6152315795314899083L;

}
