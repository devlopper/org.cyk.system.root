package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class ElectronicMailAddressDetails extends AbstractContactDetails<ElectronicMailAddress> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	@Input @InputText protected String address;
	
	public ElectronicMailAddressDetails(ElectronicMailAddress electronicMailAddress) {
		super(electronicMailAddress);
	}
	
	@Override
	public void setMaster(ElectronicMailAddress electronicMailAddress) {
		super.setMaster(electronicMailAddress);
		if(electronicMailAddress!=null){
			address = electronicMailAddress.getAddress();
		}
	}
	
	public static final String FIELD_ADDRESS = "address";

}
