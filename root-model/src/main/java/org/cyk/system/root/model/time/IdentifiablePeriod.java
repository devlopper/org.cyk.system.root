package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @NoArgsConstructor @Entity
public class IdentifiablePeriod extends AbstractIdentifiable implements Serializable {
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name=COLUMN_IDENTIFIABLE_PERIOD_TYPE) private IdentifiablePeriodType type;

	/**/
	
	@Override
	public IdentifiablePeriod setBirthDate(Date date) {
		return (IdentifiablePeriod) super.setBirthDate(date);
	}
	
	@Override
	public IdentifiablePeriod setBirthDateFromString(String date) {
		return (IdentifiablePeriod) super.setBirthDateFromString(date);
	}
	
	@Override
	public IdentifiablePeriod setDeathDate(Date date) {
		return (IdentifiablePeriod) super.setDeathDate(date);
	}
	
	@Override
	public IdentifiablePeriod setDeathDateFromString(String date) {
		return (IdentifiablePeriod) super.setDeathDateFromString(date);
	}
	
	/**/
	
	public static final String FIELD_IDENTIFIABLE_PERIOD_TYPE = "type";
	
	public static final String COLUMN_IDENTIFIABLE_PERIOD_TYPE = FIELD_IDENTIFIABLE_PERIOD_TYPE;
}
