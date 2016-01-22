package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.INTERNAL)
public class MovementCollection extends AbstractCollection<Movement> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;
	
	@Column(precision=20,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value = BigDecimal.ZERO;
 
	@OneToOne @JoinColumn(name="theinterval") @NotNull private Interval interval;
	
	@OneToOne @NotNull private MovementAction incrementAction;
	
	@OneToOne @NotNull private MovementAction decrementAction;
	
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
		return String.format(LOG_FORMAT, code,value,interval.getLogMessage());
	}
	
	public static final String LOG_FORMAT = MovementCollection.class.getSimpleName()+"(C=%s V=%s %s)";
	
	public static final String FIELD_VALUE = "value";
}
