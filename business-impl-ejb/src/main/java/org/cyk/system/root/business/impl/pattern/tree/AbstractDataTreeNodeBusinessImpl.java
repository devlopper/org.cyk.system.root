package org.cyk.system.root.business.impl.pattern.tree;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeNodeDao;

public abstract class AbstractDataTreeNodeBusinessImpl<ENUMERATION extends AbstractDataTreeNode,DAO extends AbstractDataTreeNodeDao<ENUMERATION>>  
    extends AbstractEnumerationBusinessImpl<ENUMERATION, DAO> implements AbstractDataTreeNodeBusiness<ENUMERATION> {

    public AbstractDataTreeNodeBusinessImpl(DAO dao) {
        super(dao);
    }

}
