package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class MovementCollection extends AbstractCollection<Movement> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;
	
	@Column(precision=20,scale=FLOAT_SCALE) private BigDecimal value;
 
	@OneToOne @JoinColumn(name="theinterval") private Interval interval;
	
	@OneToOne private MovementAction incrementAction;
	
	@OneToOne private MovementAction decrementAction;
	
	private Boolean supportDocumentIdentifier = Boolean.FALSE;
	
	//TODO to be model using a class which can be called MovementCollectionAlert or something like that. really i do not know so to think about
	//@Column(precision=10,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal minimalQuantityAlert = BigDecimal.ZERO;
	//@Column(precision=10,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal minimalQuantityBlock = BigDecimal.ZERO;

	public MovementCollection(String code, BigDecimal value,Interval interval) {
		super(code, code, null, null);
		this.value = value;
		this.interval = interval;
	}
	
	@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT, getCode(),value,interval==null?Constant.EMPTY_STRING:interval.getLogMessage());
	}
	
	public static final String LOG_FORMAT = MovementCollection.class.getSimpleName()+"(C=%s V=%s %s)";
	
	public static final String FIELD_VALUE = "value";
}
