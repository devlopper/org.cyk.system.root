package org.cyk.system.root.business.impl.network;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.network.ServiceBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
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

	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<Service> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Service.class);
			addFieldCodeName();
			addParameterArrayElementString(Service.FIELD_COMPUTER,Service.FIELD_PORT,Service.FIELD_AUTHENTICATED,Service.FIELD_SECURED);
		}
		
	}
}
