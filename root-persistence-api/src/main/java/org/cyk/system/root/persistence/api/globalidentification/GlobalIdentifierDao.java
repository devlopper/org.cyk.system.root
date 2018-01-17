package org.cyk.system.root.persistence.api.globalidentification;

import java.util.Collection;
import java.util.Map;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.FilterHelper.Filter;

public interface GlobalIdentifierDao {

	GlobalIdentifier read(String identifier);
	GlobalIdentifier read(String identifier,Map<String,Object> hints);
	
	GlobalIdentifier readByCode(String identifier);
	GlobalIdentifier readByCode(String identifier,Map<String,Object> hints);
	
	GlobalIdentifier create(GlobalIdentifier globalIdentifier);
	GlobalIdentifier update(GlobalIdentifier globalIdentifier);
	GlobalIdentifier delete(GlobalIdentifier globalIdentifier);
	Collection<GlobalIdentifier> readAll();
	Long countAll();
	
	DataReadConfiguration getDataReadConfig();
	
	Collection<GlobalIdentifier> readByFilter(Filter<GlobalIdentifier> filter, DataReadConfiguration dataReadConfiguration);
	Long countByFilter(Filter<GlobalIdentifier> filter, DataReadConfiguration dataReadConfiguration);
}
