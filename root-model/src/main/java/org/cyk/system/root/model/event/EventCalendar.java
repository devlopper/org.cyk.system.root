package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.TimeZone;

@Getter @Setter @Entity @NoArgsConstructor
public class EventCalendar extends AbstractEnumeration implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	@ManyToOne
	private Location location;
	
	@ManyToOne
	private TimeZone timeZone;
	
}
