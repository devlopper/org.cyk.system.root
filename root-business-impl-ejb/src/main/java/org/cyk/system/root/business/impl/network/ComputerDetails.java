package org.cyk.system.root.business.impl.network;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.network.Computer;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ComputerDetails extends AbstractOutputDetails<Computer> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Input @InputText private String ipAddress,ipAddressName;
	
	public ComputerDetails(Computer computer) {
		super(computer);
	}
	
	@Override
	public void setMaster(Computer computer) {
		super.setMaster(computer);
		if(computer!=null){
			ipAddress = computer.getIpAddress();
			ipAddressName = computer.getIpAddressName();
		}
	}
	
	public static final String FIELD_IP_ADDRESS = "ipAddress";
	public static final String FIELD_IP_ADDRESS_NAME = "ipAddressName";
}
