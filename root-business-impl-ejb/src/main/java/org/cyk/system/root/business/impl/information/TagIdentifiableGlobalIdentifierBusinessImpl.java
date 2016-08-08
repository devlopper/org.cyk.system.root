package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.information.TagIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.information.TagIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.information.TagIdentifiableGlobalIdentifierDao;

@Stateless
public class TagIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<TagIdentifiableGlobalIdentifier, TagIdentifiableGlobalIdentifierDao,TagIdentifiableGlobalIdentifier.SearchCriteria> implements TagIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public TagIdentifiableGlobalIdentifierBusinessImpl(TagIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	@Override
	public TagIdentifiableGlobalIdentifier create(TagIdentifiableGlobalIdentifier tagIdentifiableGlobalIdentifier) {
		if(tagIdentifiableGlobalIdentifier.getTag().getIdentifier()==null)
			RootBusinessLayer.getInstance().getTagBusiness().create(tagIdentifiableGlobalIdentifier.getTag());
		return super.create(tagIdentifiableGlobalIdentifier);
	}
	
	@Override
	public TagIdentifiableGlobalIdentifier update(TagIdentifiableGlobalIdentifier tagIdentifiableGlobalIdentifier) {
		RootBusinessLayer.getInstance().getTagBusiness().update(tagIdentifiableGlobalIdentifier.getTag());
		return super.update(tagIdentifiableGlobalIdentifier);
	}
	
	@Override
	public TagIdentifiableGlobalIdentifier delete(TagIdentifiableGlobalIdentifier tagIdentifiableGlobalIdentifier) {
		RootBusinessLayer.getInstance().getTagBusiness().delete(tagIdentifiableGlobalIdentifier.getTag());
		tagIdentifiableGlobalIdentifier.setTag(null);
		tagIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(null);
		return super.delete(tagIdentifiableGlobalIdentifier);
	}
	
}
