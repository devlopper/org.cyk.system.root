package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;

@Entity 
@Getter @Setter
public class Schedule extends AbstractCollection<ScheduleItem> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
}
