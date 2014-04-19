package org.cyk.system.root.business.impl.geography;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.persistence.api.geography.LocalityDao;

public class LocalityBusinessImpl extends AbstractDataTreeBusinessImpl<Locality,LocalityDao,LocalityType> implements LocalityBusiness {

    public LocalityBusinessImpl(LocalityDao dao) {
        super(dao);
    }
 
}
