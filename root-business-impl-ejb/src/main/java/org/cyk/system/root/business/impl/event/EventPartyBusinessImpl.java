package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventPartyBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.event.EventMissedDao;
import org.cyk.system.root.persistence.api.event.EventPartyDao;

public class EventPartyBusinessImpl extends AbstractTypedBusinessService<EventParty, EventPartyDao> implements EventPartyBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private EventMissedDao eventMissedDao;
	
	@Inject
	public EventPartyBusinessImpl(EventPartyDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)//TODO is Support needed ?
	public void setMissedEvent(Collection<EventParty> eventParties) {
		Collection<EventMissed> eventMisseds = eventMissedDao.readByEventParties(eventParties);
		for(EventMissed eventMissed : eventMisseds){
			for(EventParty eventParty : eventParties){
				if(eventMissed.getEventParty().equals(eventParty))
					eventParty.setMissed(eventMissed);
			}
			
		}
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public EventParty findByEventByParty(Event event, Party party) {
		return dao.readByEventByParty(event,party);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<EventParty> findByParty(Party party) {
		return dao.readByParty(party);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<EventParty> findByEvent(Event event) {
		return dao.readByEvent(event);
	}

}
