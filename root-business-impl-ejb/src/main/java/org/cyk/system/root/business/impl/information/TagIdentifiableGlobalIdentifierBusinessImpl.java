package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.TagIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.information.TagIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.information.TagIdentifiableGlobalIdentifierDao;

public class TagIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<TagIdentifiableGlobalIdentifier, TagIdentifiableGlobalIdentifierDao,TagIdentifiableGlobalIdentifier.SearchCriteria> implements TagIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public TagIdentifiableGlobalIdentifierBusinessImpl(TagIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
}
