package org.cyk.system.root.business.impl.pattern.tree;

import javax.inject.Inject;

import org.cyk.system.root.business.api.pattern.tree.DataTreeBusiness;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.DataTreeDao;

public class DataTreeBusinessImpl extends AbstractDataTreeBusinessImpl<AbstractDataTree<DataTreeType>,DataTreeDao,DataTreeType> implements DataTreeBusiness {
 
    @Inject
    public DataTreeBusinessImpl(DataTreeDao dao) {
        super(dao);
    }
 
}
