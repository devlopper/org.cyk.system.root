package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.ElectronicMailAddress;

@Getter @Setter
public class ElectronicMailAddressDetails extends AbstractContactDetails<ElectronicMailAddress> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	public ElectronicMailAddressDetails(ElectronicMailAddress electronicMailAddress) {
		super(electronicMailAddress);
	}

}
