package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.BigDecimalValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
public class MetricValue extends AbstractIdentifiable implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @NotNull private Metric metric;
	
	/*
	 * The solution to support value type is to defined each possible usable type of value.
	 * Depending on the business anyone of those value fields can be filled.
	 * A value field will be named as xxxValue where xxx is the type of value
	 */
	
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=BigDecimalValue.FIELD_USER,column=@Column(name="number_value_user"))
			,@AttributeOverride(name=BigDecimalValue.FIELD_SYSTEM,column=@Column(name="number_value_system"))
			,@AttributeOverride(name=BigDecimalValue.FIELD_GAP,column=@Column(name="number_value_gap"))
	})
	private BigDecimalValue numberValue = new BigDecimalValue();
	
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
	
	public BigDecimalValue getNumberValue(){
		if(numberValue==null)
			numberValue = new BigDecimalValue();
		return numberValue;
	}

	public static final String FIELD_METRIC = "metric";
	
	/*
	@Override
	public String toString() {
		return numberValue == null ? stringValue : numberValue.toString();
	}*/
}