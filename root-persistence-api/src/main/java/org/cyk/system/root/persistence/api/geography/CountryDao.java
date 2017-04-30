package org.cyk.system.root.persistence.api.geography;

import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.persistence.api.TypedDao;

public interface CountryDao extends TypedDao<Country> {

	Country readByCode(String code);
	
}
