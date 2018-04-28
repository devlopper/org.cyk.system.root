package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.TagBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.information.Tag;
import org.cyk.system.root.persistence.api.information.TagDao;

public class TagBusinessImpl extends AbstractEnumerationBusinessImpl<Tag,TagDao> implements TagBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public TagBusinessImpl(TagDao dao) {
        super(dao);
    } 

	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<Tag> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Tag.class);
		}
	}
}
