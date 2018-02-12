package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Entity
@Getter @Setter
public class IdentifiablePeriodType extends AbstractEnumeration implements Serializable{
	private static final long serialVersionUID = 374208919427476791L;
	
	@ManyToOne @JoinColumn(name=COLUMN_TIME_DIVISION_TYPE) @NotNull private TimeDivisionType timeDivisionType;
	
	public static final String FIELD_TIME_DIVISION_TYPE = "timeDivisionType";
	
	public static final String COLUMN_TIME_DIVISION_TYPE = FIELD_TIME_DIVISION_TYPE;
}

