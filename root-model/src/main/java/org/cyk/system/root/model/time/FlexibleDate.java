package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable @Getter @Setter
public class FlexibleDate implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;

	@Column(name="flexible_date_year")
	private Integer year;
	
	@Column(name="flexible_date_month")
	private Integer month;
	
	@Column(name="flexible_date_day")
	private Integer day;
}
