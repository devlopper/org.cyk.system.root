package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.persistence.api.geography.CountryDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class CountryDaoImpl extends AbstractTypedDao<Country> implements CountryDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	protected String readByLocalityCode;
	
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByLocalityCode, _select().where("locality.globalIdentifier.code",Country.FIELD_LOCALITY));
    }

	@Override
	public Country readByCode(String code) {
		return namedQuery(readByLocalityCode).parameter(Country.FIELD_LOCALITY, code).ignoreThrowable(NoResultException.class).resultOne();
	}
 
}
 