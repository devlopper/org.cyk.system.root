package org.cyk.system.root.business.api.chart;

import java.math.BigDecimal;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface CartesianModelListener<ENTITY extends AbstractIdentifiable>{
	
	BigDecimal y(ENTITY entity);
	Boolean skipY(BigDecimal y);

}