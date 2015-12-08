package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractOutputDetails<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7439361240545541931L;

	protected RootBusinessLayer rootBusinessLayer = RootBusinessLayer.getInstance();
	protected NumberBusiness numberBusiness = rootBusinessLayer.getNumberBusiness();
	protected TimeBusiness timeBusiness = rootBusinessLayer.getTimeBusiness();
	
	@Getter @Setter protected IDENTIFIABLE master;
	
	public AbstractOutputDetails(IDENTIFIABLE master) {
		super();
		this.master = master;
	}
	
	protected String formatNumber(Number number) {
		return numberBusiness.format(number);
	}
	protected String formatDate(Date date) {
		return timeBusiness.formatDate(date);
	}
	protected String formatDateTime(Date date) {
		return timeBusiness.formatDateTime(date);
	}
	
	protected String formatResponse(Boolean value){
		return rootBusinessLayer.getLanguageBusiness().findResponseText(value);
	}
	
}
