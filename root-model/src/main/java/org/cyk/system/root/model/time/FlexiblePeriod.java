package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;

/**
 * A period is a time interval from a start date to an end date
 * @author Christian Yao Komenan
 *
 */
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FlexiblePeriod implements Serializable{

	private static final long serialVersionUID = 1L;

	//private String timeZone;
	
	@Embedded
	@IncludeInputs
	@OutputSeperator
	@AttributeOverrides(value={
			@AttributeOverride(name="year",column=@Column(name="flexible_from_date_year")),
			@AttributeOverride(name="month",column=@Column(name="flexible_from_date_month")),
			@AttributeOverride(name="day",column=@Column(name="flexible_from_date_day"))
	})
	protected FlexibleDate fromDate = new FlexibleDate();
	
	@Embedded
	@IncludeInputs
	@OutputSeperator
	@AttributeOverrides(value={
			@AttributeOverride(name="year",column=@Column(name="flexible_to_date_year")),
			@AttributeOverride(name="month",column=@Column(name="flexible_to_date_month")),
			@AttributeOverride(name="day",column=@Column(name="flexible_to_date_day"))
	})
	protected FlexibleDate toDate = new FlexibleDate();

}