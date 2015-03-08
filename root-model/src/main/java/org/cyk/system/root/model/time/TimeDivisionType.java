package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;

@Entity
@Getter @Setter
public class TimeDivisionType extends AbstractEnumeration implements Serializable{
	
	private static final long serialVersionUID = 374208919427476791L;
	
	public static final String DAY = "DAY";
	public static final String WEEK = "WEEK";
	public static final String MONTH = "MONTH";
	public static final String TRIMESTER = "TRIMESTER";
	public static final String SEMESTER = "SEMESTER";
	public static final String YEAR = "YEAR";
	
	/**
	 * in millisecond
	 */
	@Column(nullable=false) @NotNull
	@Input @InputNumber
	private Long duration;
	
	public TimeDivisionType() {}

	public TimeDivisionType(String code,String name, String abbreviation) {
		super(code,name, abbreviation,null);
	}
	
	public TimeDivisionType(String code,String name,Long duration,Boolean constant) {
		super(code,name, name,null);
		this.duration = duration;
		this.constant = constant;
	}
}

