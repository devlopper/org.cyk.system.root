package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.cyk.system.root.model.AbstractModelElement;

import lombok.Getter;
import lombok.Setter;

@Embeddable @Getter @Setter
public class FlexibleTime extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;

	@Column(name="flexible_time_hour")
	private Integer hour;
	
	@Column(name="flexible_time_minute")
	private Integer minute;

	@Override
	public String getUiString() {
		return hour+" "+minute;
	}
	
}
