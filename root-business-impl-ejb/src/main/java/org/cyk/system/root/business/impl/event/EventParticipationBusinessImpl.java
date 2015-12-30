package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventParticipationBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.event.EventMissedDao;
import org.cyk.system.root.persistence.api.event.EventParticipationDao;

@Stateless
public class EventParticipationBusinessImpl extends AbstractTypedBusinessService<EventParticipation, EventParticipationDao> implements EventParticipationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private EventMissedDao eventMissedDao;
	
	@Inject
	public EventParticipationBusinessImpl(EventParticipationDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)//TODO is Support needed ?
	public void setMissedEvent(Collection<EventParticipation> eventParticipations) {
		Collection<EventMissed> eventMisseds = eventMissedDao.readByEventParticipations(eventParticipations);
		for(EventMissed eventMissed : eventMisseds){
			for(EventParticipation eventParticipation : eventParticipations){
				if(eventMissed.getParticipation().equals(eventParticipation))
					eventParticipation.setMissed(eventMissed);
			}
			
		}
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public EventParticipation findByEventByParty(Event event, Party party) {
		return dao.readByEventByParty(event,party);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<EventParticipation> findByParty(Party party) {
		return dao.readByParty(party);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<EventParticipation> findByEvent(Event event) {
		return dao.readByEvent(event);
	}

}
