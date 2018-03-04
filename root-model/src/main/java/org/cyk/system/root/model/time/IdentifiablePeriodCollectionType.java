package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.InstanceHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=AbstractDataTreeType.FIELD___PARENT__,type=IdentifiablePeriodCollectionType.class)
public class IdentifiablePeriodCollectionType extends AbstractDataTreeType implements Serializable  {
	private static final long serialVersionUID = -6838401709866343401L;

	@ManyToOne @JoinColumn(name=COLUMN_TIME_DIVISION_TYPE) @NotNull private TimeDivisionType timeDivisionType;
	@ManyToOne @JoinColumn(name=COLUMN_NUMBER_OF_NOT_CLOSED_AT_TIME_INTERVAL) private Interval numberOfNotClosedAtTimeInterval;
	
	@Accessors(chain=true) private Boolean isAdjacent = Boolean.TRUE;
	@Accessors(chain=true) private Boolean isDisjoint = Boolean.TRUE;
	
	/**/
	
	public IdentifiablePeriodCollectionType setTimeDivisionTypeFromCode(String code){
		this.timeDivisionType = InstanceHelper.getInstance().getByIdentifier(TimeDivisionType.class, code, ClassHelper.Listener.IdentifierType.BUSINESS);
		return this;
	}
	
	public static final String FIELD_NUMBER_OF_NOT_CLOSED_AT_TIME_INTERVAL = "numberOfNotClosedAtTimeInterval";
	public static final String FIELD_TIME_DIVISION_TYPE = "timeDivisionType";
	public static final String FIELD_IS_DISJOINT = "isDisjoint";
	public static final String FIELD_IS_ADJACENT = "isAdjacent";
	
	public static final String COLUMN_TIME_DIVISION_TYPE = FIELD_TIME_DIVISION_TYPE;
	public static final String COLUMN_NUMBER_OF_NOT_CLOSED_AT_TIME_INTERVAL = FIELD_NUMBER_OF_NOT_CLOSED_AT_TIME_INTERVAL;
}
