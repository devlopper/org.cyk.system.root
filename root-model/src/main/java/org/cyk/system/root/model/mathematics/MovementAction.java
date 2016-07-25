package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractEnumeration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor
public class MovementAction extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -8646753247708396439L;

	public static final String INCREMENT = "increment";
	public static final String DECREMENT = "decrement";
	
	@OneToOne @JoinColumn(name="theinterval") @NotNull private Interval interval;
	
	public MovementAction(String code, String name) {
		super(code, name, null, null);
	}
	public static final String FIELD_MINIMUM_VALUE = "minimumValue";
	public static final String FIELD_MAXIMUM_VALUE = "maximumValue";

	@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT, getCode(),interval.getLogMessage());
	}
	
	public static final String LOG_FORMAT = MovementAction.class.getSimpleName()+"(%s %s)";
}
