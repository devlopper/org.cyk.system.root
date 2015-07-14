package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Embeddable
public class MetricValue extends AbstractModelElement implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @NotNull
	private Metric metric;
	
	@Column(name="metric_value",precision=20,scale=FLOAT_SCALE) 
	private BigDecimal value;

	public MetricValue(Metric metric) {
		super();
		this.metric = metric;
	}

	@Override
	public String getUiString() {
		return metric+"="+value;
	}
	
}