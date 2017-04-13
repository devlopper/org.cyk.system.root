package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class LocalityDetails extends AbstractDataTreeDetails<Locality,LocalityType> implements Serializable {

	private static final long serialVersionUID = -4747519269632371426L;

	@Input @InputText private String residentName;
	
	public LocalityDetails(Locality locality) {
		super(locality);
	}
	
	@Override
	public void setMaster(Locality master) {
		super.setMaster(master);
		if(master!=null){
			residentName = master.getResidentName();
		}
	}
	
	public static final String FIELD_RESIDENT_NAME = "residentName";
}
