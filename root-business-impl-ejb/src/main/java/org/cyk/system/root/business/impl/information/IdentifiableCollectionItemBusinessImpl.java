package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.information.IdentifiableCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.information.IdentifiableCollection;
import org.cyk.system.root.model.information.IdentifiableCollectionItem;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionItemDao;
import org.cyk.utility.common.helper.CollectionHelper;

public class IdentifiableCollectionItemBusinessImpl extends AbstractCollectionItemBusinessImpl<IdentifiableCollectionItem, IdentifiableCollectionItemDao,IdentifiableCollection> implements IdentifiableCollectionItemBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public IdentifiableCollectionItemBusinessImpl(IdentifiableCollectionItemDao dao) {
		super(dao); 
	}
	
	@Override
	protected void afterCrud(IdentifiableCollectionItem identifiableCollectionItem, final Crud crud) {
		super.afterCrud(identifiableCollectionItem, crud);
		new CollectionHelper.Iterator.Adapter.Default<AbstractIdentifiable>(identifiableCollectionItem.getIdentifiablesElements()){
			private static final long serialVersionUID = 1L;
			protected void __executeForEach__(AbstractIdentifiable identifiable) {
				if(Crud.isCreateOrUpdate(crud)){
					inject(GenericBusiness.class).save(identifiable);
				}
			}
		}.execute();
	}
	
}
