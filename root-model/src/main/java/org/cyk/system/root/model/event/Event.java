package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;

/**
 * Something that happens
 * @author Christian Yao Komenan
 *
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Event extends AbstractIdentifiablePeriod implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	@ManyToOne private Party owner;
	@ManyToOne private EventCollection collection;
	
	 /**
     * Type
     */
    @ManyToOne private EventType type;
    /**
     * Object
     */
    private String object;
    /**
     * Description
     */
    @Column(length=1024 * 1) private String comments;
    
    /**
     * Contacts
     */
	@OneToOne protected ContactCollection contactCollection = new ContactCollection();
    
    private String htmlStyle;
    
    /**
     * Alarm
     */
    //@Embedded @Valid protected Alarm alarm = new Alarm();
    
    @Transient private Collection<EventParticipation> eventParticipations = new ArrayList<>();
   
    public Event(EventCollection collection,EventType type, String object, String comments, Period period) {
        super();
        this.collection = collection;
        this.type = type;
        this.object = object;
        this.comments = comments;
        this.period = period;
    }
    
    @Override
    public String toString() {
    	return object+" "+super.toString();
    }

}
