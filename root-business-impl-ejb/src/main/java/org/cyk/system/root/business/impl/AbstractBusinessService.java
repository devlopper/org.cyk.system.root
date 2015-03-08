package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Getter;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfig;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public abstract class AbstractBusinessService<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements BusinessService<IDENTIFIABLE, Long>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;
	
	//How to resolve circular dependency AbstractBusinessService -> ValidationPolicy -> LanguageBusiness which inherits of AbstractBusinessService
	//Singleton has been use to solve previous issue
	@Inject protected ValidationPolicy validationPolicy;	
	@Getter private DataReadConfig dataReadConfig = new DataReadConfig();
	
	@Inject protected GenericDao genericDao;
	@Inject protected TimeBusiness timeBusiness;

	protected abstract PersistenceService<IDENTIFIABLE, Long> getPersistenceService();
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public BusinessService<IDENTIFIABLE, Long> find() {
		getPersistenceService().select();
		return this;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE find(Long identifier) {
	    return getPersistenceService().read(identifier);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public BusinessService<IDENTIFIABLE, Long> find(Function function) {
		getPersistenceService().select(function);
		return this;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public BusinessService<IDENTIFIABLE, Long> where(LogicalOperator aLogicalOperator, String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		getPersistenceService().where(aLogicalOperator, anAttributeName, aValue, anArithmeticOperator);
		return this;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public BusinessService<IDENTIFIABLE, Long> where(String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		getPersistenceService().where(anAttributeName, aValue, anArithmeticOperator);
		return this;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public BusinessService<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue) {
		return where(anAttributeName, aValue, ArithmeticOperator.EQ);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public <RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType) {
		return getPersistenceService().one(resultType);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> all() {
		return getPersistenceService().all();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE one() {
		return getPersistenceService().one();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long oneLong() {
		return getPersistenceService().oneLong();
	}

	/**
	 * Utilities methods
	 */
	//load only when needed to avoid null pointer
	protected ExceptionUtils exceptionUtils(){
	    return ExceptionUtils.getInstance();
	}
	
	//TODO should be moved to time business
	protected Date universalTimeCoordinated(){
		return commonUtils.getUniversalTimeCoordinated();
	}
	
	
	
	protected static final SimpleDateFormat DATE_SHORT_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	protected static final SimpleDateFormat DATE_LONG_FORMAT = new SimpleDateFormat("EEEE , dd/MM/yyyy");
	protected static final SimpleDateFormat DATE_TIME_SHORT_FORMAT = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
	protected static final SimpleDateFormat DATE_TIME_LONG_FORMAT = new SimpleDateFormat("EEEE , dd/MM/yyyy à HH:mm");
	
	protected static Date DATE_MOST_PAST;
	protected static Date DATE_MOST_FUTURE;
	
	static {
		try {
			DATE_MOST_PAST = DateUtils.parseDate("01/01/1800", "dd/MM/yyyy");
			DATE_MOST_FUTURE = DateUtils.parseDate("01/01/9000", "dd/MM/yyyy");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
