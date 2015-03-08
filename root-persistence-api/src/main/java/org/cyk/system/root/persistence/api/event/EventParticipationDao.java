package org.cyk.system.root.persistence.api.event;

import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.persistence.api.TypedDao;

public interface EventParticipationDao extends TypedDao<EventParticipation> {

    Collection<EventParticipation> readByEvents(Collection<Event> events);
    
}