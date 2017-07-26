package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierDetails;
import org.cyk.system.root.model.time.ScheduleIdentifiableGlobalIdentifier;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScheduleIdentifiableGlobalIdentifierDetails extends AbstractJoinGlobalIdentifierDetails<ScheduleIdentifiableGlobalIdentifier> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText
	private FieldValue schedule;
	
	public ScheduleIdentifiableGlobalIdentifierDetails(ScheduleIdentifiableGlobalIdentifier scheduleIdentifiableGlobalIdentifier) {
		super(scheduleIdentifiableGlobalIdentifier);
		
	}
	
	@Override
	public void setMaster(ScheduleIdentifiableGlobalIdentifier scheduleIdentifiableGlobalIdentifier) {
		super.setMaster(scheduleIdentifiableGlobalIdentifier);
		if(scheduleIdentifiableGlobalIdentifier==null){
			
		}else{
			schedule = new FieldValue(scheduleIdentifiableGlobalIdentifier.getSchedule());
		}
	}
	
	/**/
	
	public static final String FIELD_SCHEDULE = "schedule";
	
}