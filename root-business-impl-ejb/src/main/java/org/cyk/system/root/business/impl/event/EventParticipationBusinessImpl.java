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
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.event.EventParticipationDao;

@Stateless
public class EventParticipationBusinessImpl extends AbstractTypedBusinessService<EventParticipation, EventParticipationDao> implements EventParticipationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public EventParticipationBusinessImpl(EventParticipationDao dao) {
		super(dao); 
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public EventParticipation findByEventByParty(Event event, Party party) {
		return dao.readByEventByParty(event,party);
	}

	@Override
	public Collection<EventParticipation> findByParty(Party party) {
		return dao.readByParty(party);
	}
	
	@Override
	public Collection<EventParticipation> findByEvent(Event event) {
		return dao.readByEvent(event);
	}

}
