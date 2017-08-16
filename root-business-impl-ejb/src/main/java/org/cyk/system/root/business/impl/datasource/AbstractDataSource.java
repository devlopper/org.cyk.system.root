package org.cyk.system.root.business.impl.datasource;

import java.io.Serializable;

import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.model.network.Service;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractDataSource extends AbstractBean implements DataSource,Serializable {

	private static final long serialVersionUID = 5757605982928616304L;

	@Getter @Setter protected String username,password,authenticationQuery,userRolesQuery,permissionsQuery,driver,scheme;
	@Getter @Setter protected Service service = new Service();
	
	public String getUrl(){
		return scheme+"://"+service.getComputer().getName()+":"+service.getPort();
	}
	
}
