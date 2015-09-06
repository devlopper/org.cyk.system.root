package org.cyk.system.root.business.api.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CartesianModel extends AbstractChartModel implements Serializable {

	private static final long serialVersionUID = 5062440475941734842L;

	private Collection<Series> seriesCollection = new ArrayList<>();
	
	private Axis xAxis = new Axis(),yAxis=new Axis();
	
	public CartesianModel(String title,String xAxisLabel,String yAxisLabel){
		this.title = title;
		xAxis.setLabel(xAxisLabel);
		yAxis.setLabel(yAxisLabel);
	}
	
	public Series addSeries(String label){
		Series series = new Series(label);
		seriesCollection.add(series);
		return series;
	}
	
}
