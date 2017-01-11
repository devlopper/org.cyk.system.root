package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.IdentifiableCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.information.IdentifiableCollection;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionDao;

public class IdentifiableCollectionBusinessImpl extends AbstractEnumerationBusinessImpl<IdentifiableCollection,IdentifiableCollectionDao> implements IdentifiableCollectionBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public IdentifiableCollectionBusinessImpl(IdentifiableCollectionDao dao) {
        super(dao);
    } 

}
