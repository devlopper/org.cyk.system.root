package org.cyk.system.root.business.impl.geography;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.persistence.api.geography.LocalityDao;

public class LocalityBusinessImpl extends AbstractDataTreeBusinessImpl<Locality,LocalityDao,LocalityType> implements LocalityBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public LocalityBusinessImpl(LocalityDao dao) {
        super(dao);
    }
	
}
