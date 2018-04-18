package org.cyk.system.root.persistence.api.party;

import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.model.party.StoreType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeDao;

public interface StoreDao extends AbstractDataTreeDao<Store,StoreType> { 

}
