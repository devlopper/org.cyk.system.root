package org.cyk.system.root.persistence.api.store;

import org.cyk.system.root.model.store.Store;
import org.cyk.system.root.model.store.StoreType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeDao;

public interface StoreDao extends AbstractDataTreeDao<Store,StoreType> { 

}
