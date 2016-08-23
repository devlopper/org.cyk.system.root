package org.cyk.system.root.business.api.geography;

import java.util.Collection;
import java.util.List;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.geography.Country;

public interface CountryBusiness extends TypedBusiness<Country> {

	Collection<Country> instanciateMany(List<String[]> list);
	
}
