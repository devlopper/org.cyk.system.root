package org.cyk.system.root.business.impl.language;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.language.LanguageCollectionBusiness;
import org.cyk.system.root.business.api.language.LanguageCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.language.LanguageCollection;
import org.cyk.system.root.model.language.LanguageCollectionItem;
import org.cyk.system.root.persistence.api.language.LanguageCollectionDao;
import org.cyk.system.root.persistence.api.language.LanguageCollectionItemDao;

public class LanguageCollectionBusinessImpl extends AbstractCollectionBusinessImpl<LanguageCollection,LanguageCollectionItem, LanguageCollectionDao,LanguageCollectionItemDao,LanguageCollectionItemBusiness> implements LanguageCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private LanguageCollectionItemDao languageCollectionItemDao;
	
	@Inject
	public LanguageCollectionBusinessImpl(LanguageCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	protected LanguageCollectionItemDao getItemDao() {
		return languageCollectionItemDao;
	}
	@Override
	protected LanguageCollectionItemBusiness getItemBusiness() {
		return inject(LanguageCollectionItemBusiness.class);
	}
	

}
