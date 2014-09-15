package org.cyk.system.root.business.impl.file;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.TagBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.file.Tag;
import org.cyk.system.root.persistence.api.file.TagDao;

public class TagBusinessImpl extends AbstractDataTreeTypeBusinessImpl<Tag,TagDao> implements TagBusiness {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public TagBusinessImpl(TagDao dao) {
        super(dao);
    } 

}
