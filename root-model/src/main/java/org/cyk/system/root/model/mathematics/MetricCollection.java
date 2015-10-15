package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor
public class MetricCollection extends AbstractCollection<Metric> implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;

	public MetricCollection(String code, String name) {
		super(code, name, null, null);
	}

}
