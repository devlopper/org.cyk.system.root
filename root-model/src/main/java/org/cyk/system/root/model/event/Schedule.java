package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

/**
 * To Plan a thing to occur at or during a particular time or period
 * @author Christian Yao Komenan
 *
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Schedule extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * The day of the week
	 */
	protected Byte day;
	/**
	 * The start hour
	 */
	protected Byte startHour;
	/**
	 * The start minute
	 */
	protected Byte startMinute;
	/**
	 * The end hour
	 */
	protected Byte endHour;
	/**
	 * The end minute
	 */
	protected Byte endMinute;
	/**
	 * The period
	 */
	@Embedded protected Period period = new Period();

}