package org.cyk.system.root.service.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.dao.api.PersistenceService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.service.api.BusinessService;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public abstract class AbstractService<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements BusinessService<IDENTIFIABLE, Long>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;
	
	protected PersistenceService<IDENTIFIABLE, Long> persistenceService;
	
	@Override
	public BusinessService<IDENTIFIABLE, Long> find() {
		persistenceService.select();
		return this;
	}
	
	@Override
	public BusinessService<IDENTIFIABLE, Long> find(Function function) {
		persistenceService.select(function);
		return this;
	}
	
	@Override
	public BusinessService<IDENTIFIABLE, Long> where(LogicalOperator aLogicalOperator, String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		persistenceService.where(aLogicalOperator, anAttributeName, aValue, anArithmeticOperator);
		return this;
	}

	@Override
	public BusinessService<IDENTIFIABLE, Long> where(String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		persistenceService.where(anAttributeName, aValue, anArithmeticOperator);
		return this;
	}
	
	@Override
	public BusinessService<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue) {
		return where(anAttributeName, aValue, ArithmeticOperator.EQ);
	}

	@Override
	public <RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType) {
		return persistenceService.one(resultType);
	}
	
	@Override
	public Collection<IDENTIFIABLE> all() {
		return persistenceService.all();
	}

	@Override
	public IDENTIFIABLE one() {
		return persistenceService.one();
	}
	
	@Override
	public Long oneLong() {
		return persistenceService.oneLong();
	}
	
}
