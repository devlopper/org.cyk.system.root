package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.party.person.AbstractMedicalInformationsJoin;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.persistence.api.party.person.AbstractMedicalInformationsJoinDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractMedicalInformationsJoinDaoImpl<INFORMATIONS extends AbstractMedicalInformationsJoin> extends AbstractTypedDao<INFORMATIONS> implements AbstractMedicalInformationsJoinDao<INFORMATIONS>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	protected String readByMedicalInformations;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByMedicalInformations, _select().where(AbstractMedicalInformationsJoin.FIELD_INFORMATIONS));
	}
	
	@Override
	public Collection<INFORMATIONS> readByMedicalInformations(MedicalInformations medicalInformations) {
		return namedQuery(readByMedicalInformations).parameter(AbstractMedicalInformationsJoin.FIELD_INFORMATIONS, medicalInformations).resultMany();
	}

}
 