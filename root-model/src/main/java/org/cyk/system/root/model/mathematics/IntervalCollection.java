package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter
@Entity 
public class IntervalCollection  extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;

	@Transient private Collection<Interval> intervals;
}