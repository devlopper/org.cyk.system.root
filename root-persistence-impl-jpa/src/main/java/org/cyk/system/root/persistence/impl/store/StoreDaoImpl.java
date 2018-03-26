package org.cyk.system.root.persistence.impl.store;

import org.cyk.system.root.model.store.Store;
import org.cyk.system.root.model.store.StoreType;
import org.cyk.system.root.persistence.api.store.StoreDao;
import org.cyk.system.root.persistence.impl.pattern.tree.AbstractDataTreeDaoImpl;

public class StoreDaoImpl extends AbstractDataTreeDaoImpl<Store,StoreType> implements StoreDao {
	private static final long serialVersionUID = 6920278182318788380L;

}
