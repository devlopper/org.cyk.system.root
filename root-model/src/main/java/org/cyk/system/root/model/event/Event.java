package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

/**
 * Something that happens
 * @author Christian Yao Komenan
 *
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Event extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	@ManyToOne private EventCollection collection;
	
	 /**
     * Type
     */
	@Input
    @ManyToOne protected EventType type;
    /**
     * Title
     */
	@Input
	@InputText
    protected String title;
    /**
     * Description
     */
	@Input
	@InputTextarea
    @Column(length=1024 * 1) protected String description;
    
    /**
     * The period
     */
	@Input
    @IncludeInputs
    @Embedded protected Period period = new Period();
    
    /**
     * Contacts
     */
	@OneToOne protected ContactCollection contactCollection = new ContactCollection();
    
    private String colorHexadecimal;
    
    /**
     * Alarm
     */
    @Embedded protected Alarm alarm = new Alarm();
    
   
    public Event(EventCollection collection,EventType type, String title, String description, Period period) {
        super();
        this.collection = collection;
        this.type = type;
        this.title = title;
        this.description = description;
        this.period = period;
    }
    
    @Override
    public String toString() {
    	return title+" "+period;
    }

}
