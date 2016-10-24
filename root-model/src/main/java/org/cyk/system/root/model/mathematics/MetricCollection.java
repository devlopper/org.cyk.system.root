package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class MetricCollection extends AbstractCollection<Metric> implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;

	@OneToOne private IntervalCollection valueIntervalCollection;
	@Enumerated(EnumType.ORDINAL) @NotNull private MetricValueType valueType = MetricValueType.NUMBER;
	@Enumerated(EnumType.ORDINAL) @NotNull private MetricValueInputted valueInputted = MetricValueInputted.VALUE_INTERVAL_VALUE;
	
	public MetricCollection(String code, String name) {
		super(code, name, null, null);
	}
	
	public MetricCollection setMetricValueType(MetricValueType valueType){
		this.valueType = valueType;
		return this;
	}
	
	public MetricCollection setMetricValueInputted(MetricValueInputted valueInputted){
		this.valueInputted = valueInputted;
		return this;
	}
	
	public static final String FIELD_VALUE_INTERVAL_COLLECTION = "valueIntervalCollection";
	public static final String FIELD_VALUE_TYPE = "valueType";
	public static final String FIELD_VALUE_INPUTTED = "valueInputted";
	
}
