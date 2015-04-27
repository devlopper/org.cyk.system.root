package org.cyk.system.root.business.api.chart;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ProportionModel extends AbstractChartModel implements Serializable {

	private static final long serialVersionUID = 5062440475941734842L;

	protected Series series = new Series();
	protected Boolean showLabels = Boolean.TRUE; 
		
	public ProportionModel(String title){
		super(title);
	}
	
}
