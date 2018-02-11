package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.IdentifiableCollectionBusiness;
import org.cyk.system.root.business.api.information.IdentifiableCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.information.IdentifiableCollection;
import org.cyk.system.root.model.information.IdentifiableCollectionItem;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionDao;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionItemDao;

public class IdentifiableCollectionBusinessImpl extends AbstractCollectionBusinessImpl<IdentifiableCollection,IdentifiableCollectionItem,IdentifiableCollectionDao,IdentifiableCollectionItemDao,IdentifiableCollectionItemBusiness> implements IdentifiableCollectionBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public IdentifiableCollectionBusinessImpl(IdentifiableCollectionDao dao) {
        super(dao);
    } 

}
