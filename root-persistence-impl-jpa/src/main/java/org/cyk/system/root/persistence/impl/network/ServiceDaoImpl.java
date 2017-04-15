package org.cyk.system.root.persistence.impl.network;

import java.io.Serializable;

import org.cyk.system.root.model.network.Service;
import org.cyk.system.root.persistence.api.network.ServiceDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class ServiceDaoImpl extends AbstractTypedDao<Service> implements ServiceDao , Serializable {

	private static final long serialVersionUID = 1L;

}
