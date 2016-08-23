package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.TagBusiness;
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
	
	@Override
	public TagIdentifiableGlobalIdentifier create(TagIdentifiableGlobalIdentifier tagIdentifiableGlobalIdentifier) {
		if(tagIdentifiableGlobalIdentifier.getTag().getIdentifier()==null)
			inject(TagBusiness.class).create(tagIdentifiableGlobalIdentifier.getTag());
		return super.create(tagIdentifiableGlobalIdentifier);
	}
	
	@Override
	public TagIdentifiableGlobalIdentifier update(TagIdentifiableGlobalIdentifier tagIdentifiableGlobalIdentifier) {
		inject(TagBusiness.class).update(tagIdentifiableGlobalIdentifier.getTag());
		return super.update(tagIdentifiableGlobalIdentifier);
	}
	
	@Override
	public TagIdentifiableGlobalIdentifier delete(TagIdentifiableGlobalIdentifier tagIdentifiableGlobalIdentifier) {
		inject(TagBusiness.class).delete(tagIdentifiableGlobalIdentifier.getTag());
		tagIdentifiableGlobalIdentifier.setTag(null);
		tagIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(null);
		return super.delete(tagIdentifiableGlobalIdentifier);
	}
	
}
