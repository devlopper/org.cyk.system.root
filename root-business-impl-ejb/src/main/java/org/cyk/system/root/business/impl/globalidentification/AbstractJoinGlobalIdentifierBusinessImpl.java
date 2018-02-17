package org.cyk.system.root.business.impl.globalidentification;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;

public abstract class AbstractJoinGlobalIdentifierBusinessImpl<IDENTIFIABLE extends AbstractJoinGlobalIdentifier,DAO extends JoinGlobalIdentifierDao<IDENTIFIABLE, SEARCH_CRITERIA>,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> extends AbstractTypedBusinessService<IDENTIFIABLE, DAO> implements JoinGlobalIdentifierBusiness<IDENTIFIABLE,SEARCH_CRITERIA>,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	protected Class<AbstractIdentifiable> joinIdentifiableClass;
	
	public AbstractJoinGlobalIdentifierBusinessImpl(DAO dao) {
		super(dao); 
		//if(StringUtils.endsWith(clazz.getName(), "IdentifiableGlobalIdentifier"))
		//	joinIdentifiableClass = getJoinIdentifiableClass();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IDENTIFIABLE create(AbstractIdentifiable join, AbstractIdentifiable identifiableJoined) {
		IDENTIFIABLE identifiable = instanciateOne();
		String joinFieldName = ClassHelper.getInstance().getVariableName(getJoinIdentifiableClass());
		if(inject(BusinessInterfaceLocator.class).injectTyped(getJoinIdentifiableClass()).isNotIdentified(join)){
			identifiable.setCascadeOperationToMaster(Boolean.TRUE);
			identifiable.setCascadeOperationToMasterFieldNames(Arrays.asList(joinFieldName));
			identifiable.setOnDeleteCascadeToJoin(Boolean.TRUE);
		}
		FieldHelper.getInstance().set(identifiable, join, joinFieldName);
		identifiable.setIdentifiableGlobalIdentifier(identifiableJoined.getGlobalIdentifier());
		return create(identifiable);
	}

	@SuppressWarnings("unchecked")
	protected Class<AbstractIdentifiable> getJoinIdentifiableClass(){
		if(joinIdentifiableClass==null){
			if(StringUtils.endsWith(clazz.getName(), "IdentifiableGlobalIdentifier"))
				joinIdentifiableClass = (Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(StringUtils.substringBefore(clazz.getName(), "IdentifiableGlobalIdentifier"));
		}
		return joinIdentifiableClass;
	}
	
	@Override
	protected void afterDelete(IDENTIFIABLE identifiable) {
		super.afterDelete(identifiable);
		identifiable.setIdentifiableGlobalIdentifier(null);
		if(Boolean.TRUE.equals(identifiable.getOnDeleteCascadeToJoin())){
			Object join = FieldHelper.getInstance().read(identifiable, ClassHelper.getInstance().getVariableName(getJoinIdentifiableClass()));
			inject(GenericBusiness.class).delete((AbstractIdentifiable)join);
		}
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
