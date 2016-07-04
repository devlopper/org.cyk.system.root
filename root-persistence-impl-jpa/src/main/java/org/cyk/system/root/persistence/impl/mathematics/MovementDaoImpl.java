package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;

public class MovementDaoImpl extends AbstractCollectionItemDaoImpl<Movement,MovementCollection> implements MovementDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readBySupportingDocumentIdentifier;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readBySupportingDocumentIdentifier, _select().where(Movement.FIELD_SUPPORTING_DOCUMENT_IDENTIFIER));
	}
	
	@Override
	public Movement readBySupportingDocumentIdentifier(String supportingDocumentIdentifier) {
		return namedQuery(readBySupportingDocumentIdentifier).ignoreThrowable(NoResultException.class)
				.parameter(Movement.FIELD_SUPPORTING_DOCUMENT_IDENTIFIER, supportingDocumentIdentifier).resultOne();
	}
}
 