package org.cyk.system.root.persistence.impl.geography;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.cyk.system.root.persistence.impl.pattern.tree.AbstractDataTreeDaoImpl;

public class LocalityDaoImpl extends AbstractDataTreeDaoImpl<Locality,LocalityType> implements LocalityDao {
	private static final long serialVersionUID = 6920278182318788380L;

}
