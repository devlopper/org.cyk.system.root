package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.utility.common.cdi.AbstractBean;

@NoArgsConstructor
public abstract class AbstractModelElementOutputDetails<MODEL_ELEMENT extends AbstractModelElement> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7439361240545541931L;

	protected RootBusinessLayer rootBusinessLayer = RootBusinessLayer.getInstance();
	protected NumberBusiness numberBusiness = inject(NumberBusiness.class);
	protected TimeBusiness timeBusiness = inject(TimeBusiness.class);
	
	@Getter protected MODEL_ELEMENT master;
	
	public AbstractModelElementOutputDetails(MODEL_ELEMENT master) {
		super();
		setMaster(master);
	}
	
	public void setMaster(MODEL_ELEMENT master){
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
		return  inject(LanguageBusiness.class).findResponseText(value);
	}
	
	protected String formatUsingBusiness(Object object) {
		return rootBusinessLayer.getFormatterBusiness().format(object);
	}
	
	/**/
	
}
