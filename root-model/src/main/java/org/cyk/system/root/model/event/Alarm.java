package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.cyk.system.root.model.time.Period;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * To warm about something
 * @author Christian Yao Komenan
 *
 */
@Embeddable
@Getter @Setter @NoArgsConstructor
public class Alarm implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Enabled = true means can be executed , no otherwise
	 */
	@Column(name="alarm_enabled")
	private Boolean enabled;
	
	/**
	 * From : The first execution date
	 * To : Behind this date no execution is allowed
	 */
	@AttributeOverrides({
	    @AttributeOverride(name="fromDate", column=@Column(name="alarm_startDate")),
	    @AttributeOverride(name="toDate", column=@Column(name="alarm_endDate"))
	  })
	@Embedded private Period period = new Period();
	
	/**
     * The number of millisecond execution should take
     */
    @Column(name="alarm_numberOfMillisecondExecution")
    private Long numberOfMillisecondExecution;
	
	/**
	 * The number of millisecond between the previous and next execution date of the alarm
	 */
	@Column(name="alarm_numberOfMillisecondBetweenExecutions")
	private Long numberOfMillisecondBetweenExecutions;
	/**
	 * Number of times execution should happen
	 */
	@Column(name="alarm_numberOfExecution")
	private Byte numberOfExecution;
	
	
}