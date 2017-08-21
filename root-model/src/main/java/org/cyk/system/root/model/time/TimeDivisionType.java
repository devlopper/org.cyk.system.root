package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.value.Measure;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class TimeDivisionType extends AbstractEnumeration implements Serializable{
	
	private static final long serialVersionUID = 374208919427476791L;
	
	@ManyToOne @NotNull private Measure measure;
	
	public static final String FIELD_MEASURE = "measure";
}

