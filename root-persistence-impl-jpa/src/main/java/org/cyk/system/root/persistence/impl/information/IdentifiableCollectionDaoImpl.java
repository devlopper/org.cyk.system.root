package org.cyk.system.root.persistence.impl.information;

import java.io.Serializable;

import org.cyk.system.root.model.information.IdentifiableCollection;
import org.cyk.system.root.model.information.IdentifiableCollectionItem;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class IdentifiableCollectionDaoImpl extends AbstractCollectionDaoImpl<IdentifiableCollection,IdentifiableCollectionItem> implements IdentifiableCollectionDao,Serializable {

	private static final long serialVersionUID = 6152315795314899083L;

}
