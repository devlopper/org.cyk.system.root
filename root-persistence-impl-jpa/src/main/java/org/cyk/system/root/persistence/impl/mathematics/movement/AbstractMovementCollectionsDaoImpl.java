package org.cyk.system.root.persistence.impl.mathematics.movement;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.AbstractMovementCollectionsDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public abstract class AbstractMovementCollectionsDaoImpl<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractCollectionDaoImpl<COLLECTION,ITEM> implements AbstractMovementCollectionsDao<COLLECTION,ITEM>,Serializable {
	private static final long serialVersionUID = 6152315795314899083L;

}
