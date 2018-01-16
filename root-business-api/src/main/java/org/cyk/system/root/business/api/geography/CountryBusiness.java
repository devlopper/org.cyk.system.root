package org.cyk.system.root.business.api.geography;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.geography.Country;

public interface CountryBusiness extends TypedBusiness<Country> {

	void setContinent(Collection<Country> countries);
	void setContinent(Country...countries);	
}
