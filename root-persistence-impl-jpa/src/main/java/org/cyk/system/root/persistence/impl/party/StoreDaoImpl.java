package org.cyk.system.root.persistence.impl.party;

import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.model.party.StoreType;
import org.cyk.system.root.persistence.api.party.StoreDao;
import org.cyk.system.root.persistence.impl.pattern.tree.AbstractDataTreeDaoImpl;

public class StoreDaoImpl extends AbstractDataTreeDaoImpl<Store,StoreType> implements StoreDao {
	private static final long serialVersionUID = 6920278182318788380L;

}
