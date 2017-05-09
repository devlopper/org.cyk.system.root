package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	@Column(name=COLUMN_VALUE,precision=20,scale=FLOAT_SCALE) private BigDecimal value;
 
	@ManyToOne @JoinColumn(name=COLUMN_INTERVAL) private Interval interval;
	
	@ManyToOne @JoinColumn(name=COLUMN_INCREMENT_ACTION) private MovementAction incrementAction;
	
	@ManyToOne @JoinColumn(name=COLUMN_DECREMENT_ACTION) private MovementAction decrementAction;
	
	private Boolean supportDocumentIdentifier = Boolean.FALSE;
	
	@ManyToOne @JoinColumn(name=COLUMN_DOCUMENT_IDENTIFIER_COUNT_INTERVAL) private Interval documentIdentifierCountInterval;
	
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
	public static final String FIELD_INTERVAL = "interval";
	public static final String FIELD_INCREMENT_ACTION = "incrementAction";
	public static final String FIELD_DECREMENT_ACTION = "decrementAction";
	public static final String FIELD_SUPPORT_DOCUMENT_IDENTIFIER = "supportDocumentIdentifier";
	public static final String FIELD_DOCUMENT_IDENTIFIER_COUNT_INTERVAL = "documentIdentifierCountInterval";
	
	public static final String COLUMN_VALUE = COLUMN_NAME_UNKEYWORD+FIELD_VALUE;
	public static final String COLUMN_INTERVAL = COLUMN_NAME_UNKEYWORD+FIELD_INTERVAL;
	public static final String COLUMN_INCREMENT_ACTION = FIELD_INCREMENT_ACTION;
	public static final String COLUMN_DECREMENT_ACTION = FIELD_DECREMENT_ACTION;
	public static final String COLUMN_SUPPORT_DOCUMENT_IDENTIFIER = FIELD_SUPPORT_DOCUMENT_IDENTIFIER;
	public static final String COLUMN_DOCUMENT_IDENTIFIER_COUNT_INTERVAL = FIELD_DOCUMENT_IDENTIFIER_COUNT_INTERVAL;
}
