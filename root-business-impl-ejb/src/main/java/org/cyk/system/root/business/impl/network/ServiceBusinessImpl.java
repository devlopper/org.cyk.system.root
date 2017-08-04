package org.cyk.system.root.business.impl.network;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.network.ServiceBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.network.Service;
import org.cyk.system.root.persistence.api.network.ServiceDao;

public class ServiceBusinessImpl extends AbstractTypedBusinessService<Service,ServiceDao> implements ServiceBusiness , Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	public ServiceBusinessImpl(ServiceDao dao) {
		super(dao);
	}
	
	@Override
	protected void beforeCreate(Service service) {
		super.beforeCreate(service);	
		if(StringUtils.isBlank(service.getCode()))
			service.setCode(service.getComputer().getCode());
		if(StringUtils.isBlank(service.getName()))
			service.setName(service.getComputer().getName());
	}

	@Override
	protected Service __instanciateOne__(String[] values,InstanciateOneListener<Service> listener) {
		listener.getInstance().getGlobalIdentifierCreateIfNull();
		set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE);
		set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_NAME);
    	set(listener.getSetListener(), Service.FIELD_COMPUTER);
    	set(listener.getSetListener(), Service.FIELD_PORT);
    	set(listener.getSetListener(), Service.FIELD_AUTHENTICATED);
    	set(listener.getSetListener(), Service.FIELD_SECURED);
    	return listener.getInstance();
	}
}
