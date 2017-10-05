package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractModelElementOutputDetails;
import org.cyk.system.root.model.geography.GlobalPosition;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class GlobalPositionDetails extends AbstractModelElementOutputDetails<GlobalPosition> implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private String longitude,latitude,altitude;
	
	public GlobalPositionDetails(GlobalPosition globalPosition) {
		super(globalPosition);
	}
	
	@Override
	public void setMaster(GlobalPosition globalPosition) {
		super.setMaster(globalPosition);
		if(globalPosition==null){
			
		}else{
			longitude = formatNumber(globalPosition.getLongitude());
			latitude = formatNumber(globalPosition.getLatitude());
			altitude = formatNumber(globalPosition.getAltitude());
		}
	}
	
	public static final String FIELD_LONGITUDE = "longitude";
	public static final String FIELD_LATITUDE = "latitude";
	public static final String FIELD_ALTITUDE = "altitude";
}
