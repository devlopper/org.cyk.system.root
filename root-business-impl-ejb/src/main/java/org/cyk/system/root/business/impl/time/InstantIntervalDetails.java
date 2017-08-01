package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractModelElementOutputDetails;
import org.cyk.system.root.model.time.InstantInterval;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class InstantIntervalDetails extends AbstractModelElementOutputDetails<InstantInterval> implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private String from,to,distanceInMillisecond,portionInMillisecond;
	
	public InstantIntervalDetails(InstantInterval instantInterval) {
		super(instantInterval);
	}
	
	@Override
	public void setMaster(InstantInterval instantInterval) {
		super.setMaster(instantInterval);
		if(instantInterval==null){
			
		}else{
			set(instantInterval);
		}
	}
	
	public InstantIntervalDetails set(InstantInterval instantInterval){
		from = instantInterval.getFrom().getUiString();
		to = instantInterval.getTo().getUiString();
		distanceInMillisecond = formatNumber(instantInterval.getDistanceInMillisecond());
		portionInMillisecond = formatNumber(instantInterval.getPortionInMillisecond());
		return this;
	}
	
	@Override
	public String toString() {
		return from+" - "+to;
	}
	
	public static final String FIELD_FROM = "from";
	public static final String FIELD_TO = "to";
	public static final String FIELD_DISTANCE_IN_MILLISECOND = "distanceInMillisecond";
	public static final String FIELD_PORTION_IN_MILLISECOND = "portionInMillisecond";
	
}
