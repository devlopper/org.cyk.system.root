package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.annotation.FieldOverride;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=AbstractDataTreeType.FIELD___PARENT__,type=IdentifiablePeriodCollectionType.class)
public class IdentifiablePeriodCollectionType extends AbstractDataTreeType implements Serializable  {
	private static final long serialVersionUID = -6838401709866343401L;

	@ManyToOne @JoinColumn(name=COLUMN_TIME_DIVISION_TYPE) @NotNull @Accessors(chain=true) private TimeDivisionType timeDivisionType;
	@ManyToOne @JoinColumn(name=COLUMN_PERIOD_DURATION_TYPE) @Accessors(chain=true) private DurationType periodDurationType;
	@ManyToOne @JoinColumn(name=COLUMN_NUMBER_OF_NOT_CLOSED_AT_TIME_INTERVAL) @Accessors(chain=true) private Interval numberOfNotClosedAtTimeInterval;
	@ManyToOne @JoinColumn(name=COLUMN_NUMBER_OF_MILLISECOND_OF_GAP_INTERVAL) @Accessors(chain=true) private Interval numberOfMillisecondOfGapInterval;
	@Column(name=COLUMN_AUTOMATICALLY_CREATE_IDENTIFIABLE_PERIOD_WHEN_NONE_FOUND) private Boolean automaticallyCreateIdentifiablePeriodWhenNoneFound;
	@Column(name=COLUMN_AUTOMATICALLY_SET_CREATED_IDENTIFIABLE_PERIOD_EXISTENCE_FROM_DATE_TO_NOW_IF_NULL) private Boolean automaticallySetCreatedIdentifiablePeriodExistenceFromDateToNowIfNull = Boolean.TRUE;
	@Column(name=COLUMN_AUTOMATICALLY_CLOSE_IDENTIFIABLE_PERIOD_WHEN_DURATION_OVER) private Boolean automaticallyCloseIdentifiablePeriodWhenDurationOver;
	
	/**/
	
	public IdentifiablePeriodCollectionType setTimeDivisionTypeFromCode(String code){
		this.timeDivisionType = getFromCode(TimeDivisionType.class, code);
		return this;
	}
	
	public IdentifiablePeriodCollectionType setPeriodDurationTypeFromCode(String code){
		this.periodDurationType = getFromCode(DurationType.class, code);
		return this;
	}
	
	public IdentifiablePeriodCollectionType setNumberOfNotClosedAtTimeIntervalFromCode(String code){
		this.numberOfNotClosedAtTimeInterval = getFromCode(Interval.class, code);
		return this;
	}
	
	public static final String FIELD_TIME_DIVISION_TYPE = "timeDivisionType";
	public static final String FIELD_PERIOD_DURATION_TYPE = "periodDurationType";
	public static final String FIELD_NUMBER_OF_NOT_CLOSED_AT_TIME_INTERVAL = "numberOfNotClosedAtTimeInterval";
	public static final String FIELD_NUMBER_OF_MILLISECOND_OF_GAP_INTERVAL = "numberOfMillisecondOfGapInterval";
	public static final String FIELD_AUTOMATICALLY_CREATE_IDENTIFIABLE_PERIOD_WHEN_NONE_FOUND = "automaticallyCreateIdentifiablePeriodWhenNoneFound";
	public static final String FIELD_AUTOMATICALLY_SET_CREATED_IDENTIFIABLE_PERIOD_EXISTENCE_FROM_DATE_TO_NOW_IF_NULL = "automaticallySetCreatedIdentifiablePeriodExistenceFromDateToNowIfNull";
	public static final String FIELD_AUTOMATICALLY_CLOSE_IDENTIFIABLE_PERIOD_WHEN_DURATION_OVER = "automaticallyCloseIdentifiablePeriodWhenDurationOver";
	
	public static final String COLUMN_TIME_DIVISION_TYPE = FIELD_TIME_DIVISION_TYPE;
	public static final String COLUMN_PERIOD_DURATION_TYPE = FIELD_PERIOD_DURATION_TYPE;
	public static final String COLUMN_NUMBER_OF_NOT_CLOSED_AT_TIME_INTERVAL = FIELD_NUMBER_OF_NOT_CLOSED_AT_TIME_INTERVAL;
	public static final String COLUMN_NUMBER_OF_MILLISECOND_OF_GAP_INTERVAL = FIELD_NUMBER_OF_MILLISECOND_OF_GAP_INTERVAL;
	
	public static final String COLUMN_AUTOMATICALLY_CREATE_IDENTIFIABLE_PERIOD_WHEN_NONE_FOUND = "autoCreatePeriodWhenNoneFound";
	public static final String COLUMN_AUTOMATICALLY_SET_CREATED_IDENTIFIABLE_PERIOD_EXISTENCE_FROM_DATE_TO_NOW_IF_NULL = "autoSetCreatedPeriodFromDateToNowIfNull";
	public static final String COLUMN_AUTOMATICALLY_CLOSE_IDENTIFIABLE_PERIOD_WHEN_DURATION_OVER = "autoClosePeriodWhenDurationOver";
}
