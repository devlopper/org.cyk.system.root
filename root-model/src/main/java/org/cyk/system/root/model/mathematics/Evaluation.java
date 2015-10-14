package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @Entity  @NoArgsConstructor
public class Evaluation  extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;

	@ManyToOne private IntervalCollection intervalCollection;
	
	@Transient private Collection<EvaluationItem> items;
	
	public Evaluation(IntervalCollection intervalCollection,String code, String name) {
		super(code, name, null, null);
		this.intervalCollection = intervalCollection;
	}
}