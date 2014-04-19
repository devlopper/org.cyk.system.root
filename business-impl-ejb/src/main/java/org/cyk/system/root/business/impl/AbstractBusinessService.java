package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;

import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfig;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public abstract class AbstractBusinessService<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements BusinessService<IDENTIFIABLE, Long>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;
	
	@Getter private DataReadConfig dataReadConfig = new DataReadConfig();
	
	protected abstract PersistenceService<IDENTIFIABLE, Long> getPersistenceService();
	
	@Override
	public BusinessService<IDENTIFIABLE, Long> find() {
		getPersistenceService().select();
		return this;
	}
	
	@Override
	public IDENTIFIABLE find(Long identifier) {
	    return getPersistenceService().read(identifier);
	}
	
	@Override
	public BusinessService<IDENTIFIABLE, Long> find(Function function) {
		getPersistenceService().select(function);
		return this;
	}
	
	@Override
	public BusinessService<IDENTIFIABLE, Long> where(LogicalOperator aLogicalOperator, String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		getPersistenceService().where(aLogicalOperator, anAttributeName, aValue, anArithmeticOperator);
		return this;
	}

	@Override
	public BusinessService<IDENTIFIABLE, Long> where(String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		getPersistenceService().where(anAttributeName, aValue, anArithmeticOperator);
		return this;
	}
	
	@Override
	public BusinessService<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue) {
		return where(anAttributeName, aValue, ArithmeticOperator.EQ);
	}

	@Override
	public <RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType) {
		return getPersistenceService().one(resultType);
	}
	
	@Override
	public Collection<IDENTIFIABLE> all() {
		return getPersistenceService().all();
	}

	@Override
	public IDENTIFIABLE one() {
		return getPersistenceService().one();
	}
	
	@Override
	public Long oneLong() {
		return getPersistenceService().oneLong();
	}

    
	
}
