package org.cyk.system.root.business.impl.file.report;

import java.io.Serializable;
import java.util.Date;

import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractReportTableRow extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6250896775642285064L;

	protected RootBusinessLayer rootBusinessLayer = RootBusinessLayer.getInstance();
	protected NumberBusiness numberBusiness = rootBusinessLayer.getNumberBusiness();
	protected TimeBusiness timeBusiness = rootBusinessLayer.getTimeBusiness();
	
	protected String formatNumber(Number number) {
		return numberBusiness.format(number);
	}
	protected String formatDate(Date date) {
		return timeBusiness.formatDate(date);
	}
	protected String formatDateTime(Date date) {
		return timeBusiness.formatDateTime(date);
	}
	
	
}
