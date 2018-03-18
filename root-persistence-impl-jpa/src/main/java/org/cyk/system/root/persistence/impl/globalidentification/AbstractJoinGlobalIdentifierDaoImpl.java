package org.cyk.system.root.persistence.impl.globalidentification;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.Utils;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public abstract class AbstractJoinGlobalIdentifierDaoImpl<IDENTIFIABLE extends AbstractIdentifiable,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> extends AbstractTypedDao<IDENTIFIABLE> implements JoinGlobalIdentifierDao<IDENTIFIABLE,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByIdentifiableGlobalIdentifiers;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByIdentifiableGlobalIdentifiers, "SELECT r FROM "+clazz.getSimpleName()+" r WHERE r.identifiableGlobalIdentifier.identifier IN :"+PARAMETER_GLOBAL_IDENTIFIERS);
		registerNamedQuery(readByCriteria, getReadByCriteriaQueryString()+getReadByCriteriaOrderByString());
	}
	
	@Override
	protected void listenInstanciateJpqlBuilder(String name, JavaPersistenceQueryLanguage builder) {
		super.listenInstanciateJpqlBuilder(name, builder);
		if(readByFilter.equals(name)){
			builder.setFieldName(AbstractJoinGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER).where().and().in(GlobalIdentifier.FIELD_IDENTIFIER);
		}
	}
			
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass,QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName,arguments);
		if(ArrayUtils.contains(new String[]{readByFilter,countByFilter}, queryName)){
			AbstractJoinGlobalIdentifier.Filter<?> filter = (AbstractJoinGlobalIdentifier.Filter<?>) arguments[0];
			queryWrapper.parameterInGlobalIdentifiers( filter.getMasterIdentifiableGlobalIdentifiers(),AbstractJoinGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_IDENTIFIER);  
		}
	}
	
	protected String getReadByCriteriaQueryString(){
		return "SELECT r FROM "+clazz.getSimpleName()+" r WHERE r.identifiableGlobalIdentifier.identifier IN :"+PARAMETER_GLOBAL_IDENTIFIERS;
	}
	
	protected String getReadByCriteriaOrderByString(){
		return " ORDER BY r.globalIdentifier.orderNumber , r.identifier";
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
 