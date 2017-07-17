package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.value.ValuePropertiesBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
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
	
	@Override
	protected ValueProperties __instanciateOne__(String[] values,InstanciateOneListener<ValueProperties> listener) {
		listener.getInstance().getGlobalIdentifierCreateIfNull();
		set(listener.getSetListener(), ValueProperties.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE);
		set(listener.getSetListener(), ValueProperties.FIELD_TYPE);
		set(listener.getSetListener(), ValueProperties.FIELD_SET);
		set(listener.getSetListener(), ValueProperties.FIELD_INTERVAL_COLLECTION);
		set(listener.getSetListener(), ValueProperties.FIELD_MEASURE);
		set(listener.getSetListener(), ValueProperties.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_DERIVED);
		set(listener.getSetListener(), ValueProperties.FIELD_DERIVATION_SCRIPT);
		set(listener.getSetListener(), ValueProperties.FIELD_NULLABLE);
		set(listener.getSetListener(), ValueProperties.FIELD_NULL_STRING);
		return listener.getInstance();
	}
	
}
