package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.utility.common.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
public class MetricValue extends AbstractIdentifiable implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @NotNull private Metric metric;
	
	@ManyToOne @JoinColumn(name="thevalue") private Value value;
	
	public ValueProperties getValueProperties(){
		if(value==null)
			if(metric==null)
				return null;
			else
				return metric.getValueProperties();
		else
			if(metric==null)
				return value.getProperties();
			else 
				if(value.getProperties()==null)
					return metric.getValueProperties();
				else
					return value.getProperties();
	}
	
	@Override
	public String toString() {
		return metric.getName()+Constant.CHARACTER_EQUAL+(value == null ? null : value.toString());	
	}
	
	public static final String FIELD_METRIC = "metric";
	public static final String FIELD_VALUE = "value";
}