package org.cyk.system.root.dao.api;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface TypedIdentifiableQuery<IDENTIFIABLE extends AbstractIdentifiable> extends TypedQuery<IDENTIFIABLE,Long> {
	
}
