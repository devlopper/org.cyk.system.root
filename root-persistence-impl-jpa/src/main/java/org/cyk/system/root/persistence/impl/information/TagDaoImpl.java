package org.cyk.system.root.persistence.impl.information;

import java.io.Serializable;

import org.cyk.system.root.model.information.Tag;
import org.cyk.system.root.persistence.api.information.TagDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class TagDaoImpl extends AbstractEnumerationDaoImpl<Tag> implements TagDao,Serializable {
	private static final long serialVersionUID = 6152315795314899083L;

}
