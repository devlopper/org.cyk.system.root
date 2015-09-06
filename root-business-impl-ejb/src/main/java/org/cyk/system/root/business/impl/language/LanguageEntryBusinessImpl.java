package org.cyk.system.root.business.impl.language;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.language.LanguageEntryBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.system.root.persistence.api.language.LanguageEntryDao;

public class LanguageEntryBusinessImpl extends AbstractTypedBusinessService<LanguageEntry, LanguageEntryDao> implements LanguageEntryBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public LanguageEntryBusinessImpl(LanguageEntryDao dao) {
		super(dao); 
	} 
	
}
