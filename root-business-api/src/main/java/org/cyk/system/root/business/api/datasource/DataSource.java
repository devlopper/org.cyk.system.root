package org.cyk.system.root.business.api.datasource;

import org.cyk.system.root.model.network.Service;

public interface DataSource {

	Service getService();
	void setService(Service aService);
	
	String getScheme();
	void setScheme(String aScheme);
	
	String getAuthenticationQuery();
	void setAuthenticationQuery(String anAuthenticationQuery);
	
	String getUserRolesQuery();
	void setUserRolesQuery(String anUserRolesQuery);
	
	String getPermissionsQuery();
	void setPermissionsQuery(String aPermissionsQuery);
	
	String getDriver();
	void setDriver(String aDriver);
	
	String getUsername();
	void setUsername(String anUsername);
	
	String getPassword();
	void setPassword(String aPassword);
	
	
	String getUrl();
	
}
