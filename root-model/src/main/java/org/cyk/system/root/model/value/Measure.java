package org.cyk.system.root.model.value;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.Setter;

/**
 * a standard unit used to express the size, amount, or degree of something.
 * @author Komenan.Christian
 *
 */
@Entity @Getter @Setter @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS)
public class Measure extends AbstractEnumeration implements Serializable{
	
	private static final long serialVersionUID = 374208919427476791L;
	
	@ManyToOne @JoinColumn(name=COLUMN_TYPE) @NotNull private MeasureType type;
	@Column(nullable=false) @NotNull private BigDecimal value;
	
	public Measure() {}

	public Measure(String code,String name, MeasureType type,BigDecimal value) {
		super(code,name, null,null);
		this.type = type;
		this.value = value;
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_VALUE = "value";
	
	public static final String COLUMN_TYPE = FIELD_TYPE;
	
}

