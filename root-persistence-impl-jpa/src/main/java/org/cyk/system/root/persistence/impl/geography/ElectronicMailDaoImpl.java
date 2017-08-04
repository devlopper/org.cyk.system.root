package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.geography.ElectronicMailDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public class ElectronicMailDaoImpl extends AbstractContactDaoImpl<ElectronicMail> implements ElectronicMailDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByValue, _select().where(ElectronicMail.FIELD_ADDRESS));
    }
    
	@Override
	public Collection<ElectronicMail> readByValue(String address) {
		return castCollection(namedQuery(readByValue).parameter(ElectronicMail.FIELD_ADDRESS, address).resultMany(),ElectronicMail.class);
	}
	
	@Override
	public Long countByValue(String address) {
		return countNamedQuery(countByValue).parameter(ElectronicMail.FIELD_ADDRESS, address).resultOne();
	}
	
	@Override
	protected String getReadByCriteriaQuery(String query) {
		return super.getReadByCriteriaQuery(query)+" OR "+QueryStringBuilder.getLikeString("record.address", ":address");
	}
	
	@Override
	protected String getReadByCriteriaQueryCodeExcludedWherePart(String where) {
		return super.getReadByCriteriaQueryCodeExcludedWherePart(where)+" OR "+QueryStringBuilder.getLikeString("record.address", ":address");
	}
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameterLike(ElectronicMail.FIELD_ADDRESS, ((ElectronicMail.SearchCriteria)searchCriteria).getAddress());
	}

 
}
 