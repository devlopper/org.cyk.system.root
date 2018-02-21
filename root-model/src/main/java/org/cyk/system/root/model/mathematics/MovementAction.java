package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class MovementAction extends AbstractEnumeration implements Serializable {
	private static final long serialVersionUID = -8646753247708396439L;

	/**
	 * The action value must be in this interval
	 */
	@ManyToOne @JoinColumn(name=COLUMN_INTERVAL) @NotNull @Accessors(chain=true) private Interval interval;
	
	public static final String FIELD_INTERVAL = "interval";
	
	public static final String COLUMN_INTERVAL = "theinterval";
}
