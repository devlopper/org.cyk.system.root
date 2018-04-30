package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.value.ValuePropertiesBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.system.root.persistence.api.value.ValuePropertiesDao;

public class ValuePropertiesBusinessImpl extends AbstractTypedBusinessService<ValueProperties, ValuePropertiesDao> implements ValuePropertiesBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ValuePropertiesBusinessImpl(ValuePropertiesDao dao) {
		super(dao); 
	}
	
	@Override
	protected void beforeCreate(ValueProperties valueProperties) {
		super.beforeCreate(valueProperties);
		if(StringUtils.isBlank(valueProperties.getCode()))
			if(valueProperties.getIntervalCollection()!=null)
				valueProperties.setCode(valueProperties.getIntervalCollection().getCode());
	}
		
	/**/
	
	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<ValueProperties> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(ValueProperties.class);
			addFieldCode().addParameterArrayElementString(ValueProperties.FIELD_TYPE,ValueProperties.FIELD_SET,ValueProperties.FIELD_INTERVAL_COLLECTION
					,ValueProperties.FIELD_MEASURE/*,ValueProperties.FIELD_DERIVED*/,ValueProperties.FIELD_DERIVATION_SCRIPT,ValueProperties.FIELD_NULLABLE,ValueProperties.FIELD_NULL_STRING);
		}	
	}

}
