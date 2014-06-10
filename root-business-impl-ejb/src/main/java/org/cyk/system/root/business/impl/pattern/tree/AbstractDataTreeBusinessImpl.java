package org.cyk.system.root.business.impl.pattern.tree;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeDao;

public abstract class AbstractDataTreeBusinessImpl<ENUMERATION extends AbstractDataTree<TYPE>,DAO extends AbstractDataTreeDao<ENUMERATION,TYPE>,TYPE extends DataTreeType>  
    extends AbstractDataTreeNodeBusinessImpl<ENUMERATION, DAO> implements AbstractDataTreeBusiness<ENUMERATION,TYPE> {

    public AbstractDataTreeBusinessImpl(DAO dao) {
        super(dao);
    }

}
