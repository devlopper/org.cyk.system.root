package org.cyk.system.root.persistence.impl.globalidentification;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.Utils;

public abstract class AbstractJoinGlobalIdentifierDaoImpl<IDENTIFIABLE extends AbstractIdentifiable,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> extends AbstractTypedDao<IDENTIFIABLE> implements JoinGlobalIdentifierDao<IDENTIFIABLE,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByIdentifiableGlobalIdentifiers;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByIdentifiableGlobalIdentifiers, "SELECT r FROM "+clazz.getSimpleName()+" r WHERE r.identifiableGlobalIdentifier.identifier IN :"+PARAMETER_GLOBAL_IDENTIFIERS);
		registerNamedQuery(readByCriteria, getReadByCriteriaQueryString());
	}
	
	protected String getReadByCriteriaQueryString(){
		return "SELECT r FROM "+clazz.getSimpleName()+" r WHERE r.identifiableGlobalIdentifier.identifier IN :"+PARAMETER_GLOBAL_IDENTIFIERS;
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByIdentifiableGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers) {
		return namedQuery(readByIdentifiableGlobalIdentifiers).parameter(PARAMETER_GLOBAL_IDENTIFIERS, Utils. getGlobalIdentfierValues(globalIdentifiers)).resultMany();
	}

	@Override
	public Collection<IDENTIFIABLE> readByIdentifiableGlobalIdentifier(GlobalIdentifier globalIdentifier) {
		return readByIdentifiableGlobalIdentifiers(Arrays.asList(globalIdentifier));
	}

	@Override
	public Collection<IDENTIFIABLE> readByIdentifiableGlobalIdentifier(AbstractIdentifiable identifiable) {
		return readByIdentifiableGlobalIdentifier(identifiable.getGlobalIdentifier());
	}

	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameter(PARAMETER_GLOBAL_IDENTIFIERS, Utils.getGlobalIdentfierValues(((AbstractJoinGlobalIdentifier.AbstractSearchCriteria)searchCriteria).getGlobalIdentifiers()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<IDENTIFIABLE> readByCriteria(SEARCH_CRITERIA criteria) {
		QueryWrapper<?> queryWrapper = namedQuery(readByCriteria);
		applySearchCriteriaParameters(queryWrapper, criteria);
		return (Collection<IDENTIFIABLE>) queryWrapper.resultMany();
	}

	@Override
	public Long countByCriteria(SEARCH_CRITERIA searchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(countByCriteria);
		applySearchCriteriaParameters(queryWrapper, searchCriteria);
		return (Long) queryWrapper.resultOne();
	}
	
	/**/
	
	public static final String PARAMETER_GLOBAL_IDENTIFIERS = "globalIdentifiers";

}
 