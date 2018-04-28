package org.cyk.system.root.persistence.impl.information;

import java.io.Serializable;

import org.cyk.system.root.model.information.Entity;
import org.cyk.system.root.persistence.api.information.EntityDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class EntityDaoImpl extends AbstractEnumerationDaoImpl<Entity> implements EntityDao,Serializable {
	private static final long serialVersionUID = 6152315795314899083L;

}
