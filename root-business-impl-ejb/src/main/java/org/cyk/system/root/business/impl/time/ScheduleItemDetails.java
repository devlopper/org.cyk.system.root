package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionItemDetails;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScheduleItemDetails extends AbstractCollectionItemDetails.AbstractDefault<ScheduleItem,Schedule> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String instantInterval;
	
	public ScheduleItemDetails(ScheduleItem scheduleItem) {
		super(scheduleItem);
	}
	
	@Override
	public void setMaster(ScheduleItem scheduleItem) {
		super.setMaster(scheduleItem);
		if(scheduleItem==null){
			
		}else{
			instantInterval = new InstantIntervalDetails(scheduleItem.getInstantInterval()).toString();
		}
	}
	
	public static final String FIELD_INSTANT_INTERVAL = "instantInterval";
	
}