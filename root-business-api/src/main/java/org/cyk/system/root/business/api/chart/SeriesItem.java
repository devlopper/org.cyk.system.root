package org.cyk.system.root.business.api.chart;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter @AllArgsConstructor
public class SeriesItem implements Serializable {

	private static final long serialVersionUID = -4044592206853702971L;

	private Object x;
	private Number y;
	
	@Override
	public String toString() {
		return "("+x+" , "+y+")";
	}
	
}
