package org.cyk.system.root.business.impl.network;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.network.Service;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class ServiceDetails extends AbstractOutputDetails<Service> implements Serializable {

	private static final long serialVersionUID = 1L;

	@IncludeInputs private ComputerDetails computer = new ComputerDetails();
	@Input @InputText private String port;
	@Input @InputText private String authenticated,secured;
	
	@Override
	public void setMaster(Service service) {
		super.setMaster(service);
		if(service!=null){
			computer.setMaster(service.getComputer());
			port = formatNumber(service.getPort());
			authenticated = formatResponse(service.getAuthenticated());
			secured = formatResponse(service.getSecured());
		}
	}
	
	public static final String FIELD_COMPUTER = "computer";
	public static final String FIELD_PORT = "port";
	public static final String FIELD_AUTHENTICATED = "authenticated";
	public static final String FIELD_SECURED = "secured";
}
