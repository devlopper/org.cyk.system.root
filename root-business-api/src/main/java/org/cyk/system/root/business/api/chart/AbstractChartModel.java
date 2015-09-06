package org.cyk.system.root.business.api.chart;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractChartModel implements Serializable {

	private static final long serialVersionUID = -1976274855545695903L;

	public static enum LegendPosition{NORTH,NORTH_EAST,NORTH_WEST,SOUTH,SOUTH_EAST,SOUTH_WEST,EAST,WEST}
	
	protected String title;
	protected Boolean showTitle = Boolean.TRUE;
	protected LegendPosition legendPosition = LegendPosition.EAST;
	
	public AbstractChartModel(String title) {
		super();
		this.title = title;
	}
	
	
	
}
