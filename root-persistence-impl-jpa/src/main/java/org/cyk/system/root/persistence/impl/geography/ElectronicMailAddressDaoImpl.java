package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.geography.ElectronicMailAddressDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public class ElectronicMailAddressDaoImpl extends AbstractContactDaoImpl<ElectronicMailAddress> implements ElectronicMailAddressDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByValue, _select().where(ElectronicMailAddress.FIELD_ADDRESS));
    }
    
	@Override
	public Collection<ElectronicMailAddress> readByValue(String address) {
		return castCollection(namedQuery(readByValue).parameter(ElectronicMailAddress.FIELD_ADDRESS, address).resultMany(),ElectronicMailAddress.class);
	}
	
	@Override
	public Long countByValue(String address) {
		return countNamedQuery(countByValue).parameter(ElectronicMailAddress.FIELD_ADDRESS, address).resultOne();
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
		queryWrapper.parameterLike(ElectronicMailAddress.FIELD_ADDRESS, ((ElectronicMailAddress.SearchCriteria)searchCriteria).getAddress());
	}

 
}
 