package org.cyk.system.root.business.impl.security;

import java.io.Serializable;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.business.api.security.ApplicationPropertiesProvider;
import org.cyk.system.root.business.api.security.ShiroConfigurator;
import org.cyk.system.root.business.impl.datasource.JdbcDataSource;
import org.cyk.system.root.business.impl.datasource.JdbcDataSource.JdbcDataSourceType;

public class DefaultShiroConfigurator implements ShiroConfigurator,Serializable {

	private static final long serialVersionUID = -4468247115471838625L;

	@Getter @Setter private DataSource dataSource;
	
	public DefaultShiroConfigurator() {
		dataSource = new JdbcDataSource();
	}
	
	@Override
	public void configure(ApplicationPropertiesProvider applicationPropertiesProvider) {
		Properties applicationProperties = applicationPropertiesProvider.getProperties();
		dataSource.getService().getComputer().setName(applicationProperties.getProperty("shiro.dataSource.host"));
		dataSource.getService().setPort(Integer.parseInt(applicationProperties.getProperty("shiro.dataSource.port")));
		dataSource.setUsername(applicationProperties.getProperty("shiro.dataSource.username"));
		dataSource.setPassword(applicationProperties.getProperty("shiro.dataSource.password"));
		if(dataSource instanceof JdbcDataSource){
			JdbcDataSource jdbcDataSource = (JdbcDataSource) dataSource;
			jdbcDataSource.setDatabaseName(applicationProperties.getProperty("shiro.dataSource.databaseName"));
			jdbcDataSource.setType(JdbcDataSourceType.valueOf(applicationProperties.getProperty("shiro.dataSource.type")));
		}
	}

}
