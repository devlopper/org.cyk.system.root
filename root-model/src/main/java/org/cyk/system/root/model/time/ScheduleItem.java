package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollectionItem;

@Entity 
@Getter @Setter
public class ScheduleItem extends AbstractCollectionItem<Schedule> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	@Embedded private InstantInterval instantInterval;
	
	{
		if(instantInterval==null)
			instantInterval = new InstantInterval();
	}
	
	public InstantInterval getInstantInterval(){
		if(instantInterval==null)
			instantInterval = new InstantInterval();
		return instantInterval;
	}
	
	/**/
	
	public static final String FIELD_INSTANT_INTERVAL = "instantInterval";
	
}
