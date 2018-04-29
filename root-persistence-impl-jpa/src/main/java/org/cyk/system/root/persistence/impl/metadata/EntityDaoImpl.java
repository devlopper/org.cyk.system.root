package org.cyk.system.root.persistence.impl.metadata;

import java.io.Serializable;

import org.cyk.system.root.model.metadata.Entity;
import org.cyk.system.root.persistence.api.metadata.EntityDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class EntityDaoImpl extends AbstractEnumerationDaoImpl<Entity> implements EntityDao,Serializable {
	private static final long serialVersionUID = 6152315795314899083L;

}
