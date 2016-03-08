package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
public class MetricValue extends AbstractIdentifiable implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @NotNull private Metric metric;
	
	/*
	 * The solution to support value type is to defined each possible usable type of value.
	 * Depending on the business anyone of those value fields can be filled.
	 * A value field will be named as xxxValue where xxx is the type of value
	 */
	
	@Column(precision=20,scale=FLOAT_SCALE) 
	private BigDecimal numberValue;
	
	private String stringValue;
	
	/*
	 * The field is used to extend the type of the string value. this enable the support of any value type
	 * which can be define by the business
	 */
	private String stringValueType;

	public MetricValue(Metric metric) {
		super();
		this.metric = metric;
	}

	public static final String FIELD_METRIC = "metric";
	
	
}