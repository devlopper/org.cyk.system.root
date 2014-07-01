package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.Getter;

import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfig;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public abstract class AbstractBusinessService<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements BusinessService<IDENTIFIABLE, Long>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;
	
	// TODO How to resolve circular dependency AbstractBusinessService -> ValidationPolicy -> LanguageBusiness which inherits of AbstractBusinessService
	//@Inject protected ValidationPolicy validationPolicy;
	
	@Getter private DataReadConfig dataReadConfig = new DataReadConfig();
	protected ExceptionUtils exceptionUtils = ExceptionUtils.getInstance();
	
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
	
	protected AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>, DataTreeType> dataTreeBusinessBean(Class<AbstractDataTree<DataTreeType>> beanClass){
	    return AbstractBusinessLayer.findDataTreeBusinessBean(beanClass);
	}
	
	protected AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>, DataTreeType> dataTreeBusinessBean(AbstractDataTree<DataTreeType> bean){
	    return AbstractBusinessLayer.findDataTreeBusinessBean(bean);
	}
	
	protected TypedBusiness<AbstractIdentifiable> typedBusinessBean(Class<AbstractIdentifiable> beanClass){
        return AbstractBusinessLayer.findTypedBusinessBean(beanClass);
    }
	
}
