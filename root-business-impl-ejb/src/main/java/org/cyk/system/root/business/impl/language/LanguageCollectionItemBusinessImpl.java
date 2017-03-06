package org.cyk.system.root.business.impl.language;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.language.LanguageCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.language.LanguageCollection;
import org.cyk.system.root.model.language.LanguageCollectionItem;
import org.cyk.system.root.persistence.api.language.LanguageCollectionItemDao;

public class LanguageCollectionItemBusinessImpl extends AbstractCollectionItemBusinessImpl<LanguageCollectionItem, LanguageCollectionItemDao,LanguageCollection> implements LanguageCollectionItemBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public LanguageCollectionItemBusinessImpl(LanguageCollectionItemDao dao) {
		super(dao); 
	}
    
}
