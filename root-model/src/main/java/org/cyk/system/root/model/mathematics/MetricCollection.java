package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor
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
	
}
