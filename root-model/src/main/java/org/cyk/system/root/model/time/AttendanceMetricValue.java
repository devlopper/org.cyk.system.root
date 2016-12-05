package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.MetricValue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @Deprecated
public class AttendanceMetricValue extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@Embedded @NotNull private Attendance attendance;
	
	@OneToOne(cascade={CascadeType.ALL}) @NotNull private MetricValue metricValue;
	
	public AttendanceMetricValue(Attendance attendance,MetricValue metricValue) {
		super();
		this.attendance = attendance;
		this.metricValue = metricValue;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	/**/
	
	public static final String FIELD_ATTENDANCE = "attendance";
	public static final String FIELD_METRIC_VALUE = "metricValue";
	
	/**/
	
	public static final String NUMBER_OF_MILLISECOND_ATTENDED = "NUMBER_OF_MILLISECOND_ATTENDED";
	public static final String NUMBER_OF_MILLISECOND_MISSED = "NUMBER_OF_MILLISECOND_MISSED";
	public static final String NUMBER_OF_MILLISECOND_MISSED_JUSTIFIED = "NUMBER_OF_MILLISECOND_MISSED_JUSTIFIED";
	public static final String NUMBER_OF_MILLISECOND_SUSPENDED = "NUMBER_OF_MILLISECOND_SUSPENDED";
	public static final String NUMBER_OF_MILLISECOND_DETENTION = "NUMBER_OF_MILLISECOND_DETENTION";

}
