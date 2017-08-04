package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.TagBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.information.Tag;
import org.cyk.system.root.persistence.api.information.TagDao;

public class TagBusinessImpl extends AbstractTypedBusinessService<Tag,TagDao> implements TagBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public TagBusinessImpl(TagDao dao) {
        super(dao);
    } 

}
