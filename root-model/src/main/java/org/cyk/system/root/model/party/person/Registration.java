package org.cyk.system.root.model.party.person;

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

import org.cyk.system.root.model.AbstractModelElement;

/**
 * A period is a time interval from a start date to an end date
 * @author Christian Yao Komenan
 *
 */
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Registration extends AbstractModelElement implements Serializable{

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="registration_date",nullable=false)
	@NotNull(groups={org.cyk.utility.common.validation.System.class})
	private Date date;
	
	@NotNull(groups=org.cyk.utility.common.validation.System.class)
	@Column(name="registration_code",nullable=false,unique=true)
	private String code;
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	@Override
	public String toString() {
		return code+" "+date;
	}
	
	public static final String FIELD_DATE = "date";
	public static final String FIELD_CODE = "code";

}