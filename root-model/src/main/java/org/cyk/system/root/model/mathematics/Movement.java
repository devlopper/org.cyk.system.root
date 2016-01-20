package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollectionItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity
public class Movement extends AbstractCollectionItem<MovementCollection> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name="maction") @NotNull private MovementAction action;
	
	@Temporal(TemporalType.TIMESTAMP) @Column(name="mdate",nullable=false) @NotNull private Date date;
	
	@Column(name="mvalue",precision=20,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value;
	
	/**/
	
	@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT, date,action.getLogMessage(),value,collection.getLogMessage());
	}
	
	public static final String LOG_FORMAT = Movement.class.getSimpleName()+"(DATE=%s %s VALUE=%s %s)";
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_DATE = "date";
		
}
