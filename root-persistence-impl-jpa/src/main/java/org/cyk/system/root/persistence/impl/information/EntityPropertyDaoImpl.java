package org.cyk.system.root.persistence.impl.information;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.information.Entity;
import org.cyk.system.root.model.information.EntityProperty;
import org.cyk.system.root.model.information.Property;
import org.cyk.system.root.persistence.api.information.EntityPropertyDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public class EntityPropertyDaoImpl extends AbstractTypedDao<EntityProperty> implements EntityPropertyDao,Serializable {
	private static final long serialVersionUID = 6152315795314899083L;

	@Override
	protected void listenInstanciateJpqlBuilder(String name, JavaPersistenceQueryLanguage builder) {
		super.listenInstanciateJpqlBuilder(name, builder);
		if(readByFilter.equals(name)){
			builder.setFieldName(EntityProperty.FIELD_ENTITY).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
			builder.setFieldName(EntityProperty.FIELD_PROPERTY).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
		}
	}
			
	@SuppressWarnings("unchecked")
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass,QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName,arguments);
		if(ArrayUtils.contains(new String[]{readByFilter,countByFilter}, queryName)){
			FilterHelper.Filter<T> filter = (Filter<T>) arguments[0];
			queryWrapper.parameterInIdentifiers(filter.filterMasters(Entity.class),EntityProperty.FIELD_ENTITY,GlobalIdentifier.FIELD_IDENTIFIER);
			queryWrapper.parameterInIdentifiers(filter.filterMasters(Property.class),EntityProperty.FIELD_PROPERTY,GlobalIdentifier.FIELD_IDENTIFIER);
		}
	}
	
	@Override
	public EntityProperty readByEntityByProperty(Entity entity, Property property) {
		return CollectionHelper.getInstance().getFirst(readByFilter(new EntityProperty.Filter().addMaster(entity).addMaster(property)));
	}

}
