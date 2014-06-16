package org.cyk.system.root.model.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ScheduleEvent implements Serializable {

    private Event event;
    
    private Period period;
    
}
