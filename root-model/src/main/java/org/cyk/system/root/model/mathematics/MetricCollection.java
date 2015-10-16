package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;

@Entity @Getter @Setter @NoArgsConstructor
public class MetricCollection extends AbstractCollection<Metric> implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;

	@OneToOne private IntervalCollection valueIntervalCollection;
	
	public MetricCollection(String code, String name) {
		super(code, name, null, null);
	}
	
}
