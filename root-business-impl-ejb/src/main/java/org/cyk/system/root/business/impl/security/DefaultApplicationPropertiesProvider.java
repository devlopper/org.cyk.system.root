package org.cyk.system.root.business.impl.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import org.cyk.system.root.business.api.security.ApplicationPropertiesProvider;

public class DefaultApplicationPropertiesProvider implements ApplicationPropertiesProvider,Serializable {

	private static final long serialVersionUID = -1817072959200001087L;

	protected static final String SYSTEM_PROPERTIES_FILE_LOCATION = "META-INF/cyk.properties";
	
	@Override
	public Properties getProperties() {
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream(SYSTEM_PROPERTIES_FILE_LOCATION));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
