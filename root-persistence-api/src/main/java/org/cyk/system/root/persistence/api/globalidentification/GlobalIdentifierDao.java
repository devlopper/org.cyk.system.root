package org.cyk.system.root.persistence.api.globalidentification;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;

public interface GlobalIdentifierDao {

	GlobalIdentifier create(GlobalIdentifier globalIdentifier);
	GlobalIdentifier update(GlobalIdentifier globalIdentifier);

}
