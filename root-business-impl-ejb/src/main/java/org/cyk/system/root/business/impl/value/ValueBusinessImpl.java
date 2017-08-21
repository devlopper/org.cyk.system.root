package org.cyk.system.root.business.impl.value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.value.MeasureBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness;
import org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueSet;
import org.cyk.system.root.model.value.ValueType;
import org.cyk.system.root.persistence.api.value.ValueDao;
import org.cyk.system.root.persistence.api.value.ValuePropertiesDao;
import org.cyk.utility.common.generator.RandomDataProvider;

public class ValueBusinessImpl extends AbstractTypedBusinessService<Value, ValueDao> implements ValueBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ValueBusinessImpl(ValueDao dao) {
		super(dao); 
	}
	
	@Override
	public Value instanciateOne(String[] values) {
		Value value = instanciateOne();
		Integer index = 0;
		String valueString;
		value.setCode(values[index++]);
		value.setName(values[index++]);
		valueString = values[index++];
		if(StringUtils.isNotBlank(valueString))
			value.setProperties(inject(ValuePropertiesDao.class).read(valueString));
		return value;
	}

	@Override
	public void setRandomly(Collection<Value> values) {
		for(Value value : values){
			if(Boolean.TRUE.equals(value.getNullable()) && RandomDataProvider.getInstance().randomBoolean())
				value.set_(null);
			else{
				if(ValueType.BOOLEAN.equals(value.getType())){
					value.set_(RandomDataProvider.getInstance().randomBoolean());
				}else if(ValueType.NUMBER.equals(value.getType())){
					if(value.getIntervalCollection()==null){
						value.getNumberValue().set(new BigDecimal(RandomDataProvider.getInstance().randomInt(0, 100)));
						if(value.getMeasure()!=null){
							value.getNumberValue().set(inject(MeasureBusiness.class).computeMultiple(value.getMeasure(), value.getNumberValue().get()));
						}
					}else
						value.set_(inject(IntervalCollectionBusiness.class).generateRandomValue(value.getIntervalCollection()));
				}else if(ValueType.STRING.equals(value.getType())){
					if(ValueSet.INTERVAL_RELATIVE_CODE.equals(value.getSet()))
						if(value.getIntervalCollection()!=null){
							Interval interval = ((Interval)RandomDataProvider.getInstance().randomFromList(new ArrayList<>(inject(IntervalBusiness.class)
									.findByCollection(value.getIntervalCollection()))));
							value.set_(RootConstant.Code.getRelativeCode(interval));
						}else
							;
					else
						value.set_( RandomStringUtils.randomAlphabetic(1));
				}
			}
		}	
		inject(GenericBusiness.class).update(commonUtils.castCollection(values, AbstractIdentifiable.class));
	}

	@Override
	public Object derive(Value value,Derive listener) {
		Object oldValue = value.get();
		if(value.isDerived()){
			Script script = value.getProperties().getDerivationScript();
			script.getInputs().clear();
			if(listener!=null){
				Map<String,Object> inputs = new LinkedHashMap<>(listener.getInputs());
				listener.processInputs(value,inputs);
				script.getInputs().putAll(inputs);
			}
			value.set_(inject(ScriptBusiness.class).evaluate(script));//TODO be carefull with concurrent access
		}
		logTrace("Value {} , derivable {} , old value = {} , new value = {}", value.getName(),value.isDerived(),oldValue,value.get());
		return value.get();
	}

	@Override
	public void derive(Collection<Value> values,Derive listener) {
		for(Value value : values)
			derive(value,listener);
	}

	@Override
	public Collection<Value> deriveByCodes(Collection<String> valueCodes,Derive listener) {
		Collection<Value> values = dao.read(valueCodes);
		derive(values,listener);
		return values;
	}

	@Override
	public Value deriveByCode(String valueCode,Derive listener) {
		Value value = dao.read(valueCode);
		derive(value,listener);
		return value;
	}
	
	/**/
	
	public static interface Listener extends AbstractIdentifiableBusinessServiceImpl.Listener<Value> {
	
		/**/
		
		public static class Adapter extends AbstractIdentifiableBusinessServiceImpl.Listener.Adapter<Value> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				/**/
			
				public static class EnterpriseResourcePlanning extends Default implements Serializable {
					private static final long serialVersionUID = 1L;
					
					/**/
					
				}
				
			}
			
		}
	}

	/**/
	
	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<Value> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Value.class);
			addFieldCode().addParameterArrayElementString(Value.FIELD_PROPERTIES);
		}	
	}
}
