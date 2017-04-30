package org.cyk.system.root.business.api.chart;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Axis implements Serializable {

	private static final long serialVersionUID = -8390956358793222359L;

	private String label;
	private BigDecimal minimum=BigDecimal.ZERO,maximum;
	private Integer tickAngle,tickCount;
}
