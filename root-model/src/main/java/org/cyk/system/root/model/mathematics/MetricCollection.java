package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.system.root.model.value.ValueSet;
import org.cyk.system.root.model.value.ValueType;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class MetricCollection extends AbstractCollection<Metric> implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;

	@ManyToOne @JoinColumn(name="metricCollectionType") private MetricCollectionType type;
	
	@ManyToOne private ValueProperties valueProperties;
	
	@ManyToOne @JoinColumn(name="thevalue") private Value value;
	
	public MetricCollection(String code, String name) {
		super(code, name, null, null);
	}
	
	public MetricCollection setValueProperties(ValueProperties valueProperties){
		this.valueProperties = valueProperties;
		return this;
	}
	
	public MetricCollection setValue(Value value){
		this.value = value;
		return this;
	}

	public ValueType getValueType(){
		return valueProperties == null ? ValueType.DEFAULT : valueProperties.getType();
	}
	
	public ValueSet getValueSet(){
		return valueProperties == null ? ValueSet.DEFAULT : valueProperties.getSet();
	}
	
	public IntervalCollection getValueIntervalCollection(){
		return valueProperties == null ? null : valueProperties.getIntervalCollection();
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_VALUE_PROPERTIES = "valueProperties";
	public static final String FIELD_VALUE = "value";
	
}
