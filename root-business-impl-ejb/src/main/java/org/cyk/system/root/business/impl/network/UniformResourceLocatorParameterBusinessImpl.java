package org.cyk.system.root.business.impl.network;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.network.UniformResourceLocatorParameterBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.persistence.api.network.UniformResourceLocatorParameterDao;

public class UniformResourceLocatorParameterBusinessImpl extends AbstractTypedBusinessService<UniformResourceLocatorParameter, UniformResourceLocatorParameterDao> implements UniformResourceLocatorParameterBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public UniformResourceLocatorParameterBusinessImpl(UniformResourceLocatorParameterDao dao) {
		super(dao); 
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<UniformResourceLocatorParameter> findByUniformResourceLocator(UniformResourceLocator uniformResourceLocator) {
		return dao.readByUniformResourceLocator(uniformResourceLocator);
	}
	

}
