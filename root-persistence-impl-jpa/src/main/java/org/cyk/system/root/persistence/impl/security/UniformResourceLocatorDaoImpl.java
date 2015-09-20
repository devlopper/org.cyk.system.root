package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.persistence.api.network.UniformResourceLocatorDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class UniformResourceLocatorDaoImpl extends AbstractEnumerationDaoImpl<UniformResourceLocator> implements UniformResourceLocatorDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByPathByParameters;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		/*registerNamedQuery(readByPathByParameters, _select().where(UniformResourceLocator.FIELD_PATH)
				.openSubQueryStringBuilder(UniformResourceLocatorParameter.class).where(UniformResourceLocatorParameter.FIELD_NAME)
					.and(UniformResourceLocatorParameter.FIELD_VALUE)
				.closeSubQueryStringBuilder()
				
				);*/
	}
	
	@Override
	public UniformResourceLocator readByPathByParameters(String path, Map<String, String[]> parameters) {
		return namedQuery(readByPathByParameters).parameter(UniformResourceLocator.FIELD_PATH, path)
				.parameter(UniformResourceLocator.FIELD_PARAMETERS, parameters).ignoreThrowable(NoResultException.class).resultOne();
	}

	
	

}
 