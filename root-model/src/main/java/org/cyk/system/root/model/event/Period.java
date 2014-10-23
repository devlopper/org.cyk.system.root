package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;

/**
 * A period is a time interval from a start date to an end date
 * @author Christian Yao Komenan
 *
 */
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Period implements Serializable{

	private static final long serialVersionUID = 1L;

	//private String timeZone;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(nullable=false)
	@Input
	@InputCalendar
	protected Date fromDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(nullable=false)
	@Input
	@InputCalendar
	protected Date toDate;

}