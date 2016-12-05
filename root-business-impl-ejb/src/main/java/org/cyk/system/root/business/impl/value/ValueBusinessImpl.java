package org.cyk.system.root.business.impl.value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueSet;
import org.cyk.system.root.model.value.ValueType;
import org.cyk.system.root.persistence.api.value.ValueDao;
import org.cyk.utility.common.generator.RandomDataProvider;

public class ValueBusinessImpl extends AbstractTypedBusinessService<Value, ValueDao> implements ValueBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ValueBusinessImpl(ValueDao dao) {
		super(dao); 
	}

	@Override
	public void setManyRandomly(Collection<Value> values) {
		for(Value value : values){
			Boolean setNull = Boolean.TRUE.equals(value.getNullable()) ? RandomDataProvider.getInstance().randomBoolean() : Boolean.FALSE;
			if(ValueType.BOOLEAN.equals(value.getType())){
				value.set(Boolean.TRUE.equals(setNull) ? null : RandomDataProvider.getInstance().randomBoolean());
			}else if(ValueType.NUMBER.equals(value.getType())){
				if(Boolean.TRUE.equals(setNull))
					value.set(null);
				else if(value.getIntervalCollection()==null)
					value.set(new BigDecimal(RandomDataProvider.getInstance().randomInt(0, 100)));
				else
					value.set(inject(IntervalCollectionBusiness.class).generateRandomValue(value.getIntervalCollection()));
			}else if(ValueType.STRING.equals(value.getType())){
				if(Boolean.TRUE.equals(setNull))
					value.set(null);
				else if(ValueSet.INTERVAL_CODE.equals(value.getSet()))
					if(value.getIntervalCollection()!=null){
						Interval interval = ((Interval)RandomDataProvider.getInstance().randomFromList(new ArrayList<>(inject(IntervalBusiness.class)
								.findByCollection(value.getIntervalCollection()))));
						value.set(inject(RootBusinessLayer.class).getRelativeCode(interval.getCollection(), interval.getCode()));
					}else
						;
				else
					value.set( RandomStringUtils.randomAlphabetic(1));
			}
		}	
		inject(GenericBusiness.class).update(commonUtils.castCollection(values, AbstractIdentifiable.class));
	}

}
