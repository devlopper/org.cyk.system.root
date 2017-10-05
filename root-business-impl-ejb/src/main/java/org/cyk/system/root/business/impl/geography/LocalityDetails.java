package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;

@Getter @Setter
public class LocalityDetails extends AbstractDataTreeDetails<Locality,LocalityType> implements Serializable {

	private static final long serialVersionUID = -4747519269632371426L;

	@Input @InputText private String residentName;
	@IncludeInputs(layout=Layout.VERTICAL) private GlobalPositionDetails globalPosition = new GlobalPositionDetails();
	
	public LocalityDetails(Locality locality) {
		super(locality);
	}
	
	@Override
	public void setMaster(Locality locality) {
		super.setMaster(locality);
		if(locality!=null){
			residentName = locality.getResidentName();
			if(globalPosition==null)
				globalPosition = new GlobalPositionDetails(locality.getGlobalPosition());
			else
				globalPosition.setMaster(locality.getGlobalPosition());
		}
	}
	
	public static final String FIELD_RESIDENT_NAME = "residentName";
	public static final String FIELD_GLOBAL_POSITION = "globalPosition";
}
