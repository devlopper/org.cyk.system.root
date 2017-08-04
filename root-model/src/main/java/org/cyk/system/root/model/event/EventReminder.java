package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.time.AbstractIdentifiablePeriod;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * To warm about event
 * @author Christian Yao Komenan
 *
 */
@Entity @Getter @Setter @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class EventReminder extends AbstractIdentifiablePeriod implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static Boolean ENABLED = Boolean.TRUE;
	
	@ManyToOne @NotNull
	private Event event;
	/**
	 * Enabled = true means can be executed , no otherwise
	 */
	private Boolean enabled = ENABLED;
	/**
     * The number of millisecond execution should take
     */
    private Long numberOfMillisecondExecution;
	/**
	 * The number of millisecond between the previous and next execution date of the alarm
	 */
	private Long numberOfMillisecondBetweenExecutions;
	/**
	 * Number of times execution should happen
	 */
	private Byte numberOfExecution;
	
}