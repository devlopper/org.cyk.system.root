package org.cyk.system.root.business.impl.chart;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.chart.ChartBusiness;
import org.cyk.system.root.business.api.chart.Series;
import org.cyk.system.root.business.api.chart.SeriesItem;
import org.cyk.system.root.business.api.chart.SeriesItemListener;

public class ChartBusinessImpl implements ChartBusiness,Serializable {

	private static final long serialVersionUID = 5755767809030029525L;

	@Override
	public Series series(Collection<?> collection, SeriesItemListener seriesItemListener) {
		Series series = new Series();
		for(Object object : collection)
			series.getItems().add(new SeriesItem(seriesItemListener.x(object), seriesItemListener.y(object)));
		return series;
	}

}
