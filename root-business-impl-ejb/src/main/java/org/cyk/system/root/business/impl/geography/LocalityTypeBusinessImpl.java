package org.cyk.system.root.business.impl.geography;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.persistence.api.geography.LocalityTypeDao;

public class LocalityTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<LocalityType,LocalityTypeDao> implements LocalityTypeBusiness {

    @Inject
    public LocalityTypeBusinessImpl(LocalityTypeDao dao) {
        super(dao);
    } 

}
