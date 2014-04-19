package org.cyk.system.root.business.impl.pattern.tree;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeTypeBusiness;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeTypeDao;

public abstract class AbstractDataTreeTypeBusinessImpl<ENUMERATION extends AbstractDataTreeType,DAO extends AbstractDataTreeTypeDao<ENUMERATION>>  
    extends AbstractDataTreeNodeBusinessImpl<ENUMERATION, DAO> implements AbstractDataTreeTypeBusiness<ENUMERATION> {

    public AbstractDataTreeTypeBusinessImpl(DAO dao) {
        super(dao);
    }

}
