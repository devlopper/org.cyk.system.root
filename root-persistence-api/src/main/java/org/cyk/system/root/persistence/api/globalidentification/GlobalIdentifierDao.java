package org.cyk.system.root.persistence.api.globalidentification;

import java.util.Collection;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;

public interface GlobalIdentifierDao {

	GlobalIdentifier create(GlobalIdentifier globalIdentifier);
	GlobalIdentifier update(GlobalIdentifier globalIdentifier);
	GlobalIdentifier delete(GlobalIdentifier globalIdentifier);
	Collection<GlobalIdentifier> readAll();
	Long countAll();
}
