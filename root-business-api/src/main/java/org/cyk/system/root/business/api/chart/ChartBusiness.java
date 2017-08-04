package org.cyk.system.root.business.api.chart;

import java.util.Collection;

public interface ChartBusiness {

	Series series(Collection<?> collection,SeriesItemListener item);
	
	/**/
	
}
