package org.cyk.system.root.business.impl.globalidentification;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;

public abstract class AbstractJoinGlobalIdentifierBusinessImpl<IDENTIFIABLE extends AbstractJoinGlobalIdentifier,DAO extends JoinGlobalIdentifierDao<IDENTIFIABLE, SEARCH_CRITERIA>,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> extends AbstractTypedBusinessService<IDENTIFIABLE, DAO> implements JoinGlobalIdentifierBusiness<IDENTIFIABLE,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	protected Class<? extends AbstractIdentifiable> joinIdentifiableClass;
	
	public AbstractJoinGlobalIdentifierBusinessImpl(DAO dao) {
		super(dao); 
		//if(StringUtils.endsWith(clazz.getName(), "IdentifiableGlobalIdentifier"))
		//	joinIdentifiableClass = getJoinIdentifiableClass();
	}
	
	@SuppressWarnings("unchecked")
	protected Class<? extends AbstractIdentifiable> getJoinIdentifiableClass(){
		if(joinIdentifiableClass==null){
			if(StringUtils.endsWith(clazz.getName(), "IdentifiableGlobalIdentifier"))
				joinIdentifiableClass = (Class<? extends AbstractIdentifiable>) ClassHelper.getInstance().getByName(StringUtils.substringBefore(clazz.getName(), "IdentifiableGlobalIdentifier"));
		}
		return joinIdentifiableClass;
	}
	
	@Override
	protected void afterDelete(IDENTIFIABLE identifiable) {
		super.afterDelete(identifiable);
		identifiable.setIdentifiableGlobalIdentifier(null);	
		setJoinNull(identifiable);
	}
	
	protected void setJoinNull(IDENTIFIABLE identifiable){
		Class<?> aClass = getJoinIdentifiableClass();
		if(aClass==null){
			return;
		}
		MethodHelper.getInstance().callSet(identifiable, StringHelper.getInstance().applyCaseType(aClass.getSimpleName(), CaseType.FU), aClass, null);
	}
	
	@Override
	public Collection<IDENTIFIABLE> findByIdentifiableGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers) {
		return dao.readByIdentifiableGlobalIdentifiers(globalIdentifiers);
	}

	@Override
	public Collection<IDENTIFIABLE> findByIdentifiableGlobalIdentifier(GlobalIdentifier globalIdentifier) {
		return dao.readByIdentifiableGlobalIdentifier(globalIdentifier);
	}

	@Override
	public Collection<IDENTIFIABLE> findByIdentifiableGlobalIdentifier(AbstractIdentifiable identifiable) {
		return dao.readByIdentifiableGlobalIdentifier(identifiable);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByCriteria(final SEARCH_CRITERIA searchCriteria) {
		return dao.readByCriteria(searchCriteria);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(SEARCH_CRITERIA searchCriteria) {
		return dao.countByCriteria(searchCriteria);
	}
}
