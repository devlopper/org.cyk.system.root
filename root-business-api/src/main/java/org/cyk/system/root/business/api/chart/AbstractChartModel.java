package org.cyk.system.root.business.api.chart;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractChartModel implements Serializable {

	private static final long serialVersionUID = -1976274855545695903L;

	protected String title;
	
}
