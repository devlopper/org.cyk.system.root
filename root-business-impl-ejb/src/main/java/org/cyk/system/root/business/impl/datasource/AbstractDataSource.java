package org.cyk.system.root.business.impl.datasource;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.model.network.Service;
import org.cyk.utility.common.cdi.AbstractBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDataSource extends AbstractBean implements DataSource,Serializable {

	private static final long serialVersionUID = 5757605982928616304L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataSource.class);
	
	@Getter @Setter protected String username,password,authenticationQuery,userRolesQuery,permissionsQuery,driver,scheme;
	@Getter @Setter protected Service service = new Service();
	
	public String getUrl(){
		return scheme+"://"+service.getHost().getName()+":"+service.getPort();
	}
	
	@Override
	protected Logger __logger__() {
		return LOGGER;
	}
}
