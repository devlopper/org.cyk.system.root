package org.cyk.system.root.business.impl.datasource;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class JdbcDataSource extends AbstractDataSource implements Serializable {

	private static final long serialVersionUID = 5757605982928616304L;

	@Getter @Setter private String databaseName;
	@Getter private JdbcDataSourceType type;
	
	public JdbcDataSource() {
		authenticationQuery = "SELECT password FROM credentials WHERE username = ?";
		userRolesQuery = "SELECT roleid FROM useraccountroles WHERE useraccountid = "
				+ "(SELECT useraccount.identifier FROM useraccount,credentials WHERE useraccount.credentials_identifier=credentials.identifier AND credentials.username = ?)";
		permissionsQuery = "SELECT permissionid FROM rolepermissions WHERE roleid = "
				+ "("+userRolesQuery+")";
		
		logTrace("Authentication Query : {}",authenticationQuery);
		logTrace("User roles Query : {}",userRolesQuery);
		logTrace("Permissions Query : {}",permissionsQuery);
	}
	
	public String getUrl(){
		return super.getUrl()+"/"+databaseName;
	}
	
	@AllArgsConstructor
	public enum JdbcDataSourceType{
		MYSQL("mysql","com.mysql.jdbc.jdbc2.optional.MysqlDataSource"),
		
		;
		
		private String schemePart;
		private String driver;
	}
	
	public void setType(JdbcDataSourceType type) {
		this.type = type;
		driver = this.type.driver;
		this.scheme = "jdbc:"+type.schemePart;
	}
	
}
