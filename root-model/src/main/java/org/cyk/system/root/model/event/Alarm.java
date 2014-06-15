package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

/**
 * To warm about something
 * @author Christian Yao Komenan
 *
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Alarm extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Enabled = true means can be executed , no otherwise
	 */
	private Boolean enabled;
	
	/**
	 * From : The first execution date
	 * To : Behind this date no execution is allowed
	 */
	@Embedded private Period period = new Period();
	/**
	 * The number of millisecond between the previous and next execution date of the alarm
	 */
	private Long numberOfMillisecondBetweenExecutions;
	/**
	 * Number of times execution should happen
	 */
	private Byte numberOfExecution;
	
	
}