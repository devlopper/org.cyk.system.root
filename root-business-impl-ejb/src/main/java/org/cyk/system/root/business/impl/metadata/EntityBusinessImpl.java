package org.cyk.system.root.business.impl.metadata;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.metadata.EntityBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.metadata.Entity;
import org.cyk.system.root.persistence.api.metadata.EntityDao;

public class EntityBusinessImpl extends AbstractEnumerationBusinessImpl<Entity,EntityDao> implements EntityBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public EntityBusinessImpl(EntityDao dao) {
        super(dao);
    } 

	@Override
	public Entity findByClassName(Class<?> aClass) {
		return dao.read(aClass.getSimpleName().toUpperCase());
	}
	
	/**/
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<Entity> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Entity.class);
		}
	}

	
}
