package org.cyk.system.root.business.impl.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.value.ValueBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness.DeriveArguments;
import org.cyk.system.root.business.api.value.ValueCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;
import org.cyk.system.root.persistence.api.value.ValueCollectionItemDao;
import org.cyk.system.root.persistence.api.value.ValueDao;

public class ValueCollectionItemBusinessImpl extends AbstractCollectionItemBusinessImpl<ValueCollectionItem, ValueCollectionItemDao,ValueCollection> implements ValueCollectionItemBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ValueCollectionItemBusinessImpl(ValueCollectionItemDao dao) {
		super(dao); 
	}
	
	@Override
	protected void setAutoSettedProperties(ValueCollectionItem valueCollectionItem) {
		super.setAutoSettedProperties(valueCollectionItem);
		if(StringUtils.isBlank(valueCollectionItem.getName()))
			if(valueCollectionItem.getValue()!=null)
				valueCollectionItem.setName(valueCollectionItem.getValue().getName());
	}
	
	@Override
	public ValueCollectionItem instanciateOne(String[] values) {
		ValueCollectionItem valueCollectionItem = super.instanciateOne(values);
		Integer index = 15;
		String value;
		if(StringUtils.isNotBlank(value = values[index++]))
			valueCollectionItem.setValue(inject(ValueDao.class).read(value));
		return valueCollectionItem;
	}

	@Override
	public void derive(Collection<ValueCollectionItem> valueCollectionItems,DeriveArguments arguments) {
		Collection<Value> values = new ArrayList<>();
		for(ValueCollectionItem valueCollectionItem : valueCollectionItems)
			values.add(valueCollectionItem.getValue());
		inject(ValueBusiness.class).derive(values,arguments);
	}

	@Override
	public void derive(ValueCollectionItem valueCollectionItem,DeriveArguments arguments) {
		inject(ValueBusiness.class).derive(valueCollectionItem.getValue(),arguments);
	}

	@Override
	public Collection<ValueCollectionItem> deriveByCodes(Collection<String> valueCollectionItemCodes,DeriveArguments arguments) {
		Collection<ValueCollectionItem> valueCollectionItems = dao.read(valueCollectionItemCodes);
		derive(valueCollectionItems,arguments);
		return valueCollectionItems;
	}

	@Override
	public ValueCollectionItem deriveByCode(String valueCollectionItemCode,DeriveArguments arguments) {
		ValueCollectionItem valueCollectionItem = dao.read(valueCollectionItemCode);
		derive(valueCollectionItem,arguments);
		return valueCollectionItem;
	}
		
}
