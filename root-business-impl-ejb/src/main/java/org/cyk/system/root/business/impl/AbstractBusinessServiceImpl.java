package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBusinessServiceImpl extends AbstractBean implements BusinessService, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;
	
	private transient Logger logger; 
	@Inject protected TimeBusiness timeBusiness;
	@Inject protected NumberBusiness numberBusiness;

	@Override
	protected Logger __logger__() {
		return getLogger();
	}
	
	public Logger getLogger() {
		if(logger==null)
			logger = LoggerFactory.getLogger(getClass());
		return logger;
	}
	
	protected ExceptionUtils exceptionUtils(){
	    return ExceptionUtils.getInstance();
	}
	
	protected Date universalTimeCoordinated(){
		return timeBusiness.findUniversalTimeCoordinated();
	}

	
}
