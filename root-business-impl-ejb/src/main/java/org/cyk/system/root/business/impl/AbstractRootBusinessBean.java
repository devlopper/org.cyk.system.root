package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractRootBusinessBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6720546413687083260L;

	protected RootBusinessLayer rootBusinessLayer = RootBusinessLayer.getInstance();
	protected LanguageBusiness languageBusiness = RootBusinessLayer.getInstance().getLanguageBusiness();
	protected NumberBusiness numberBusiness = RootBusinessLayer.getInstance().getNumberBusiness();
	protected TimeBusiness timeBusiness = RootBusinessLayer.getInstance().getTimeBusiness();
	protected ContactCollectionBusiness contactCollectionBusiness = RootBusinessLayer.getInstance().getContactCollectionBusiness();
	protected ApplicationBusiness applicationBusiness = RootBusinessLayer.getInstance().getApplicationBusiness();
	
	protected String text(String code) {
		return languageBusiness.findText(code);
	}
	protected String format(Number number) {
		return numberBusiness.format(number);
	}
	protected String format(Date date) {
		return timeBusiness.formatDate(date);
	}
	
}
