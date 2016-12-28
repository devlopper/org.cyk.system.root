package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.value.ValuePropertiesBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.system.root.model.value.ValueSet;
import org.cyk.system.root.model.value.ValueType;
import org.cyk.system.root.persistence.api.file.ScriptDao;
import org.cyk.system.root.persistence.api.mathematics.IntervalCollectionDao;
import org.cyk.system.root.persistence.api.value.MeasureDao;
import org.cyk.system.root.persistence.api.value.NullStringDao;
import org.cyk.system.root.persistence.api.value.ValuePropertiesDao;

public class ValuePropertiesBusinessImpl extends AbstractTypedBusinessService<ValueProperties, ValuePropertiesDao> implements ValuePropertiesBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ValuePropertiesBusinessImpl(ValuePropertiesDao dao) {
		super(dao); 
	}

	@Override
	public ValueProperties instanciateOne(String[] values) {
		ValueProperties valueProperties = instanciateOne();
		Integer index = 0;
		String value;
		valueProperties.setCode(values[index++]);
		value = values[index++];
		if(StringUtils.isNotBlank(value))
			valueProperties.setType(ValueType.valueOf(value));
		value = values[index++];
		if(StringUtils.isNotBlank(value))
			valueProperties.setSet(ValueSet.valueOf(value));
		value = values[index++];
		if(StringUtils.isNotBlank(value))
			valueProperties.setIntervalCollection(inject(IntervalCollectionDao.class).read(value));
		value = values[index++];
		if(StringUtils.isNotBlank(value))
			valueProperties.setMeasure(inject(MeasureDao.class).read(value));
		value = values[index++];
		if(StringUtils.isNotBlank(value))
			valueProperties.setDerived(Boolean.parseBoolean(value));
		value = values[index++];
		if(StringUtils.isNotBlank(value))
			valueProperties.setDerivationScript(inject(ScriptDao.class).read(value));
		value = values[index++];
		if(StringUtils.isNotBlank(value))
			valueProperties.setNullable(Boolean.parseBoolean(value));
		value = values[index++];
		if(StringUtils.isNotBlank(value))
			valueProperties.setNullString(inject(NullStringDao.class).read(value));
		return valueProperties;
	}

}
