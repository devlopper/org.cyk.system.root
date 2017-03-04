package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.time.AbstractIdentifiablePeriod;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.time.PeriodSearchCriteria;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Something that happens
 * @author Christian Yao Komenan
 *
 */
@Entity @Getter @Setter @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class Event extends AbstractIdentifiablePeriod implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	@ManyToOne @JoinColumn(name="thetype") private EventType type;
	
    @OneToOne private ContactCollection contactCollection = new ContactCollection();
    
    @Transient private IdentifiableRuntimeCollection<EventParty> eventParties;
    @Transient private IdentifiableRuntimeCollection<EventReminder> eventReminders;
    
    public Event(Period period) {
        super();
        setExistencePeriod(period);
    }
    
    public IdentifiableRuntimeCollection<EventParty> getEventParties(){
    	if(eventParties==null)
    		eventParties = new IdentifiableRuntimeCollection<>();
    	return eventParties;
    }
    
    public IdentifiableRuntimeCollection<EventReminder> getEventReminders(){
    	if(eventReminders==null)
    		eventReminders = new IdentifiableRuntimeCollection<>();
    	return eventReminders;
    }
    
    /**/
    
    @Getter @Setter
    public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet implements Serializable {

    	private static final long serialVersionUID = 3134811510557411588L;

    	private PeriodSearchCriteria periodSearchCriteria;
    	
    	public SearchCriteria(){
    		this(null,null);
    	}
    	
    	public SearchCriteria(SearchCriteria criteria){
    		super(null);
    		this.periodSearchCriteria = new PeriodSearchCriteria(criteria.periodSearchCriteria);
    	}
    	
    	public SearchCriteria(Date fromDate,Date toDate){
    		super(null);
    		periodSearchCriteria = new PeriodSearchCriteria(fromDate,toDate);
    	}
    	
    }

}
