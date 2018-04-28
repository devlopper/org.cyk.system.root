package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.EntityBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.information.Entity;
import org.cyk.system.root.persistence.api.information.EntityDao;

public class EntityBusinessImpl extends AbstractEnumerationBusinessImpl<Entity,EntityDao> implements EntityBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public EntityBusinessImpl(EntityDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<Entity> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Entity.class);
		}
	}
}
