package org.cyk.system.root.business.api.globalidentification;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;

public interface GlobalIdentifierBusiness {

	Boolean isCreatable(Class<? extends AbstractIdentifiable> aClass);
	Boolean isReadable(AbstractIdentifiable identifiable);
	Boolean isUpdatable(AbstractIdentifiable identifiable);
	Boolean isDeletable(AbstractIdentifiable identifiable);
	
	GlobalIdentifier create(GlobalIdentifier globalIdentifier);
	GlobalIdentifier update(GlobalIdentifier globalIdentifier);
	GlobalIdentifier delete(GlobalIdentifier globalIdentifier);
	
	
}
