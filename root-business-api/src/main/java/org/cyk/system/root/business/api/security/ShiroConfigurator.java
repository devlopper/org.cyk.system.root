package org.cyk.system.root.business.api.security;

import org.cyk.system.root.business.api.datasource.DataSource;

public interface ShiroConfigurator {

	DataSource getDataSource();
	void setDataSource(DataSource aDataSource);
	
	void configure(ApplicationPropertiesProvider applicationPropertiesProvider);
	
}
